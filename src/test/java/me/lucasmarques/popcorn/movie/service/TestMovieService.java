package me.lucasmarques.popcorn.movie.service;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.impl.InMemoryActorRepository;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.impl.InMemoryDirectorRepository;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.movie.exception.CastMissingException;
import me.lucasmarques.popcorn.movie.exception.DirectedByMissingException;
import me.lucasmarques.popcorn.movie.exception.MovieAlreadyPersistedException;
import me.lucasmarques.popcorn.movie.model.Censorship;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.impl.InMemoryMovieRepository;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestMovieService {

    SystemConfig config = SystemConfig.getInstance();

    MovieService service;

    @Test
    public void testCanSaveNewMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        List<Actor> actors = new ArrayList<>();
        List<Director> directors = new ArrayList<>();

        actors.add(new Actor("Someone Smith"));
        actors.add(new Actor("Someone Silva"));
        directors.add(new Director("Foo Lano"));

        Movie movie = new Movie("Funny movie", launchDate, Rating.R, directors, actors);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        service = new MovieService(new InMemoryMovieRepository(new ArrayList<>()),
                new InMemoryDirectorRepository(new ArrayList<>(), movies, new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), movies, new HashMap<>()));

        try {
            service.save(movie);
        } catch (Exception e) {
        }

        List<Movie> result = service.findByCensorship(Censorship.CENSORED);

        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getName(), "Funny movie");
        Assert.assertEquals(result.get(0).getCast().size(), 2);
        Assert.assertEquals(result.get(0).getDirectedBy().get(0).getName(), "Foo Lano");
    }

    @Test
    public void testCannotSaveExistingMovie() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        List<Actor> actors = new ArrayList<>();
        List<Director> directors = new ArrayList<>();

        actors.add(new Actor("Someone Smith"));
        actors.add(new Actor("Someone Silva"));
        directors.add(new Director("Foo Lano"));

        Movie movie = new Movie("Funny movie", launchDate, Rating.R, directors, actors);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        service = new MovieService(new InMemoryMovieRepository(movies),
                new InMemoryDirectorRepository(new ArrayList<>(), movies, new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), movies, new HashMap<>()));

        try {
            service.save(movie);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof MovieAlreadyPersistedException);
            Assert.assertEquals(e.getMessage(), "The movie Funny movie is already persisted on database.");
        }
    }

    @Test
    public void testCannotSaveMovieWithoutDirector() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        List<Actor> actors = new ArrayList<>();
        List<Director> directors = new ArrayList<>();

        actors.add(new Actor("Someone Smith"));
        actors.add(new Actor("Someone Silva"));

        Movie movie = new Movie("Funny movie", launchDate, Rating.R, directors, actors);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        service = new MovieService(new InMemoryMovieRepository(new ArrayList<>()),
                new InMemoryDirectorRepository(new ArrayList<>(), movies, new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), movies, new HashMap<>()));

        try {
            service.save(movie);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof DirectedByMissingException);
            Assert.assertEquals(e.getMessage(), "The movie Funny movie dont have any director.");
        }
    }

    @Test
    public void testCannotSaveMovieWithoutCast() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());
        List<Actor> actors = new ArrayList<>();
        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Foo Lano"));

        Movie movie = new Movie("Funny movie", launchDate, Rating.R, directors, actors);

        List<Movie> movies = new ArrayList<>();
        movies.add(movie);

        service = new MovieService(new InMemoryMovieRepository(new ArrayList<>()),
                new InMemoryDirectorRepository(new ArrayList<>(), movies, new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), movies, new HashMap<>()));

        try {
            service.save(movie);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CastMissingException);
            Assert.assertEquals(e.getMessage(), "The movie Funny movie dont have a cast.");
        }
    }

    @Test
    public void testCanFindCensoredMovies() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Censored movie 1", launchDate, Rating.NC17));
        movies.add(new Movie("Censored movie 2", launchDate, Rating.PG13));
        movies.add(new Movie("Censored movie 3", launchDate, Rating.PG));
        movies.add(new Movie("Censored movie 4", launchDate, Rating.R));
        movies.add(new Movie("Not censored movie", launchDate, Rating.G));

        service = new MovieService(new InMemoryMovieRepository(movies),
                new InMemoryDirectorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()));

        List<Movie> result = service.findByCensorship(Censorship.CENSORED);

        Assert.assertEquals(result.size(), 4);
    }

    @Test
    public void testCanFindNotCensoredMovies() {
        ZonedDateTime launchDate = ZonedDateTime.of(2014, 10, 26, 0, 0, 0, 0, config.getTimezone());

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Censored movie 1", launchDate, Rating.NC17));
        movies.add(new Movie("Censored movie 2", launchDate, Rating.PG13));
        movies.add(new Movie("Censored movie 3", launchDate, Rating.PG));
        movies.add(new Movie("Censored movie 4", launchDate, Rating.R));
        movies.add(new Movie("Not censored movie 1", launchDate, Rating.G));
        movies.add(new Movie("Not censored movie 2", launchDate, Rating.G));
        movies.add(new Movie("Not censored movie 3", launchDate, Rating.G));

        service = new MovieService(new InMemoryMovieRepository(movies),
                new InMemoryDirectorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()));

        List<Movie> result = service.findByCensorship(Censorship.NOT_CENSORED);

        Assert.assertEquals(result.size(), 3);
    }

    @Test
    public void testCannotFindNonexistentMovies() {
        List<Movie> movies = new ArrayList<>();
        service = new MovieService(new InMemoryMovieRepository(movies),
                new InMemoryDirectorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()),
                new InMemoryActorRepository(new ArrayList<>(), new ArrayList<>(), new HashMap<>()));

        List<Movie> result1 = service.findByCensorship(Censorship.CENSORED);
        List<Movie> result2 = service.findByCensorship(Censorship.NOT_CENSORED);

        Assert.assertEquals(result1.size(), 0);
        Assert.assertEquals(result2.size(), 0);
    }

}
