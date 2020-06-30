package me.lucasmarques.popcorn.director.repository.impl;

import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class TestRelationalDatabaseDirectorRepository {

    ConnectionDriver driver = new ConnectionDriver();
    RelationalDatabaseDirectorRepository repository = new RelationalDatabaseDirectorRepository(driver);

    @After
    public void after() {
        Director director = repository.findByName("Quentin Tarantino");
        if (director != null) {
            String sql = "DELETE FROM directors WHERE id = '" + director.getId().toString() + "'";
            try {
                driver.executeSql(sql);
            } catch (SQLException e) {
            }
        }
    }

    @Test
    public void testCanSaveNewDirector() {
        Director director = new Director("Quentin Tarantino");
        Director result = repository.save(director);

        Assert.assertEquals(director, result);
    }

    @Test
    public void testCannotSaveExistingDirector() {
        Director director = new Director("James Wan");
        Director result = repository.save(director);

        Assert.assertNull(result);
    }

    @Test
    public void testCanFindDirectorByName() {
        Director director = repository.findByName("Jon Favreau");
        Assert.assertEquals(director.getName(), "Jon Favreau");
    }

    @Test
    public void testCannotFindNonexistentDirectorByName() {
        Director director = repository.findByName("Lucas Marques");
        Assert.assertNull(director);
    }

    @Test
    public void testCanFindDirectorsRelatedToAMovie() {
        // Lion King movie's id
        UUID movieId = UUID.fromString("3314fc8d-872b-4751-ae34-7e34bbc8022f");

        List<Director> directors = repository.findByMovieId(movieId);
        Assert.assertEquals(directors.size(), 1);
    }

    @Test
    public void testCannotFindDirectorsRelatedToAMovie() {
        // UUID from a non-existent movie
        UUID movieId = UUID.fromString("25de7b2c-a6b7-4da4-babc-3bec2150a2c4");

        List<Director> directors = repository.findByMovieId(movieId);

        Assert.assertEquals(directors.size(), 0);
    }

}
