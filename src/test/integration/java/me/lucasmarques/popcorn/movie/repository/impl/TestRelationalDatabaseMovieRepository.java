package me.lucasmarques.popcorn.movie.repository.impl;

import me.lucasmarques.popcorn.actor.repository.impl.RelationalDatabaseActorRepository;
import me.lucasmarques.popcorn.director.repository.impl.RelationalDatabaseDirectorRepository;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.DatabaseName;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.UUID;

public class TestRelationalDatabaseMovieRepository {

    SystemConfig config = SystemConfig.getInstance();

    ConnectionDriver driver = new ConnectionDriver();
    RelationalDatabaseDirectorRepository directorRepository = new RelationalDatabaseDirectorRepository(driver);
    RelationalDatabaseActorRepository actorRepository = new RelationalDatabaseActorRepository(driver);

    RelationalDatabaseMovieRepository repository = new RelationalDatabaseMovieRepository(driver, directorRepository, actorRepository);

    @Before
    public void before() {
        Movie movie = repository.findByName("The Vast Night");
        if (movie != null) {
            String sql = String.format("DELETE FROM %s WHERE id = '%s'", DatabaseName.MOVIES.value, movie.getId());
            try {
                driver.executeSql(sql);
            } catch (SQLException e) {
            }
        }
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
    }

    @Test
    public void testCanRelateOnlyValidActorsToAMovie() {
    }

    @Test
    public void testCannotRelateActorsToANonexistentMovie() {
    }

    @Test
    public void testCanRelateDirectorsToAMovie() {
    }

    @Test
    public void testCanRelateOnlyValidDirectorsToAMovie() {
    }

    @Test
    public void testCannotRelateDirectorsToANonexistentMovie() {
    }

    @Test
    public void testCanFindMoviesByRating() {
    }

    @Test
    public void testCannotFindNonexistentMoviesByRating() {
    }

    @Test
    public void testCanFindMoviesByNotEqualRating() {
    }

    @Test
    public void testCannotFindNonexistentMoviesByNotEqualRating() {
    }

}
