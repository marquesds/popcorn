package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class TestRelationalDatabaseActorRepository {

    RelationalDatabaseActorRepository repository = new RelationalDatabaseActorRepository(new ConnectionDriver());

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void testCanFindActorsRelatedToAMovie() {
        // Lion King movie's id
        UUID movieId = UUID.fromString("3314fc8d-872b-4751-ae34-7e34bbc8022f");

        List<Actor> actors = repository.findByMovieId(movieId);
        Assert.assertEquals(actors.size(), 8);
    }

    @Test
    public void testCannotFindActorsRelatedToAMovie() {
        // UUID from a non-existent movie
        UUID movieId = UUID.fromString("530d1a29-4216-4d84-9dde-0dbbd6a59bc9");

        List<Actor> actors = repository.findByMovieId(movieId);

        Assert.assertEquals(actors.size(), 0);
    }
}
