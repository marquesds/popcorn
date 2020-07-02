package me.lucasmarques.popcorn.movie.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.impl.RelationalDatabaseActorRepository;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.impl.RelationalDatabaseDirectorRepository;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.DatabaseName;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestRelationalDatabaseMovieRepository {

    SystemConfig config = SystemConfig.getInstance();

    ConnectionDriver driver;
    RelationalDatabaseDirectorRepository directorRepository;
    RelationalDatabaseActorRepository actorRepository;

    RelationalDatabaseMovieRepository repository;

    private void deleteMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            try {
                if (movie != null) {
                    String sql = String.format("DELETE FROM %s WHERE id = '%s'", DatabaseName.MOVIES.value, movie.getId());
                    ResultSet resultSet = driver.executeSql(sql);
                    resultSet.close();
                }
            } catch (SQLException e) {
            } finally {
                driver.close();
            }
        }
    }

    private void deleteActors(List<Actor> actors) {
        for (Actor actor : actors) {
            try {
                if (actor != null) {
                    String sql1 = String.format("DELETE FROM %s WHERE actor_id = '%s'", DatabaseName.MOVIES_ACTORS.value, actor.getId());
                    String sql2 = String.format("DELETE FROM %s WHERE id = '%s'", DatabaseName.ACTORS.value, actor.getId());

                    ResultSet resultSet1 = driver.executeSql(sql1);
                    ResultSet resultSet2 = driver.executeSql(sql2);
                    resultSet1.close();
                    resultSet2.close();
                }
            } catch (SQLException e) {
            } finally {
                driver.close();
            }
        }
    }

    private void deleteDirectors(List<Director> directors) {
        for (Director director : directors) {
            try {
                if (director != null) {
                    String sql1 = String.format("DELETE FROM %s WHERE director_id = '%s'", DatabaseName.MOVIES_DIRECTORS.value, director.getId());
                    String sql2 = String.format("DELETE FROM %s WHERE id = '%s'", DatabaseName.MOVIES.value, director.getId());

                    ResultSet resultSet1 = driver.executeSql(sql1);
                    ResultSet resultSet2 = driver.executeSql(sql2);
                    resultSet1.close();
                    resultSet2.close();
                }
            } catch (SQLException e) {
            } finally {
                driver.close();
            }
        }
    }

    public void cleanMoviesDatabase() {
        List<Movie> movies = new ArrayList<>();

        movies.add(repository.findByName("The Vast Night"));
        movies.add(repository.findByName("Interstellar"));
        movies.add(repository.findByName("Eternal Sunshine of the Spotless Mind"));

        deleteMovies(movies);
    }

    public void cleanActorsDatabase() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        deleteActors(actors);
    }

    public void cleanDirectorsDatabase() {
        List<Director> directors = new ArrayList<>();
        Director director = directorRepository.findByName("Christopher Nolan");
        directors.add(director);

        deleteDirectors(directors);
    }

    @Before
    public void before() {
        driver = new ConnectionDriver();
        directorRepository = new RelationalDatabaseDirectorRepository(driver);
        actorRepository = new RelationalDatabaseActorRepository(driver);

        repository = new RelationalDatabaseMovieRepository(driver, directorRepository, actorRepository);
    }

    @After
    public void after() {
        cleanMoviesDatabase();
        cleanActorsDatabase();
        cleanDirectorsDatabase();
    }

    @Test
    public void testCanSaveNewMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 5, 29, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("The Vast Night", launchDate, Rating.PG);

        Movie result = repository.save(movie);
        Assert.assertEquals(result, movie);
    }

    @Test
    public void testCannotSaveExistentMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 5, 29, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Lion King", launchDate, Rating.G);

        Movie result = repository.save(movie);
        Assert.assertNull(result);
    }

    @Test
    public void testCanFindMovieByName() {
        Movie movie = repository.findByName("Lion King");
        Assert.assertEquals(movie.getName(), "Lion King");
    }

    @Test
    public void testCannotFindNonexistentMovieByName() {
        Movie movie = repository.findByName("Lucas Movie");
        Assert.assertNull(movie);
    }

    @Test
    public void testCanFindMovieById() {
        Movie movie = repository.findById(UUID.fromString("3314fc8d-872b-4751-ae34-7e34bbc8022f"));
        Assert.assertEquals(movie.getName(), "Lion King");
    }

    @Test
    public void testCannotFindNonexistentMovieById() {
        Movie movie = repository.findById(UUID.fromString("ee7f5a51-4038-47ed-b25c-56ab7fe54093"));
        Assert.assertNull(movie);
    }

    @Test
    public void testCanRelateActorsToAMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate, Rating.PG));
        Movie movie = repository.findByName("Interstellar");

        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        List<Actor> savedActors = new ArrayList<>();
        for (Actor actor : actors) {
            actorRepository.save(actor);
            savedActors.add(actorRepository.findByName(actor.getName()));
        }

        Map<Movie, List<Actor>> relationship = repository.relateActorsToMovie(savedActors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 5);
    }

    @Test
    public void testCanRelateOnlyValidActorsToAMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate, Rating.PG));
        Movie movie = repository.findByName("Interstellar");

        Actor invalidActor = new Actor("Lucas Marques");

        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));
        actors.add(new Actor("Jessica Chastain"));
        actors.add(new Actor("Casey Affleck"));
        actors.add(new Actor("Wes Bentley"));

        List<Actor> savedActors = new ArrayList<>();
        for (Actor actor : actors) {
            actorRepository.save(actor);
            savedActors.add(actorRepository.findByName(actor.getName()));
        }

        savedActors.add(invalidActor);
        Map<Movie, List<Actor>> relationship = repository.relateActorsToMovie(savedActors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 5);
    }

    @Test
    public void testCannotRelateActorsToANonexistentMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG);

        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Matthew McConaughey"));
        actors.add(new Actor("Anne Hathaway"));

        List<Actor> savedActors = new ArrayList<>();
        for (Actor actor : actors) {
            actorRepository.save(actor);
            savedActors.add(actorRepository.findByName(actor.getName()));
        }

        Map<Movie, List<Actor>> relationship = repository.relateActorsToMovie(savedActors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 0);
    }

    @Test
    public void testCanRelateDirectorsToAMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate, Rating.PG));
        Movie movie = repository.findByName("Interstellar");

        directorRepository.save(new Director("Christopher Nolan"));
        List<Director> directors = new ArrayList<>();
        Director director = directorRepository.findByName("Christopher Nolan");
        directors.add(director);

        Map<Movie, List<Director>> relationship = repository.relateDirectorsToMovie(directors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 1);
    }

    @Test
    public void testCanRelateOnlyValidDirectorsToAMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate, Rating.PG));
        Movie movie = repository.findByName("Interstellar");

        directorRepository.save(new Director("Christopher Nolan"));
        List<Director> directors = new ArrayList<>();
        Director director = directorRepository.findByName("Christopher Nolan");
        Director invalidDirector = new Director("Lucas Marques");
        directors.add(director);
        directors.add(invalidDirector);

        Map<Movie, List<Director>> relationship = repository.relateDirectorsToMovie(directors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 1);
    }

    @Test
    public void testCannotRelateDirectorsToANonexistentMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        Movie movie = new Movie("Interstellar", launchDate, Rating.PG);

        directorRepository.save(new Director("Christopher Nolan"));
        List<Director> directors = new ArrayList<>();
        Director director = directorRepository.findByName("Christopher Nolan");
        directors.add(director);

        Map<Movie, List<Director>> relationship = repository.relateDirectorsToMovie(directors, movie);

        Assert.assertEquals(relationship.get(movie).size(), 0);
    }

    @Test
    public void testCanFindMoviesByRating() {
        ZonedDateTime launchDate1 = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        ZonedDateTime launchDate2 = ZonedDateTime.of(2004, 3, 19, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate1, Rating.PG));
        repository.save(new Movie("Eternal Sunshine of the Spotless Mind", launchDate2, Rating.PG));
        List<Movie> movies = repository.findByRating(Rating.PG);

        Assert.assertEquals(movies.size(), 2);
    }

    @Test
    public void testCannotFindNonexistentMoviesByRating() {
        List<Movie> movies = repository.findByRating(Rating.NC17);
        Assert.assertEquals(movies.size(), 0);
    }

    @Test
    public void testCanFindMoviesByNotEqualRating() {
        ZonedDateTime launchDate1 = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        ZonedDateTime launchDate2 = ZonedDateTime.of(2004, 3, 19, 0, 0, 0, 0, config.getTimezone());
        repository.save(new Movie("Interstellar", launchDate1, Rating.PG));
        repository.save(new Movie("Eternal Sunshine of the Spotless Mind", launchDate2, Rating.PG));
        List<Movie> movies = repository.findByNotEqualRating(Rating.PG);

        List<String> names = new ArrayList<>();
        names.add(movies.get(0).getName());
        names.add(movies.get(1).getName());

        Assert.assertEquals(movies.size(), 2);
        Assert.assertTrue(names.contains("Lion King"));
        Assert.assertTrue(names.contains("Saw"));
    }

}
