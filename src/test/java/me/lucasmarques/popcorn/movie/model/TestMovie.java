package me.lucasmarques.popcorn.movie.model;

import me.lucasmarques.popcorn.infra.config.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class TestMovie {

    private final ZoneId zoneId = SystemConfig.getInstance().getTimezone();

    @Test
    public void testMovieThatIsIndicatedForGeneralAudiences() {
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        Movie movie = new Movie("Lion King", launchDate, Rating.G, new ArrayList<>(), new ArrayList<>());

        Assert.assertTrue(movie.isIndicatedForGeneralAudiences());
    }

    @Test
    public void testMovieThatIsNotIndicatedForGeneralAudiences() {
        ZonedDateTime launchDate = ZonedDateTime.of(2004, 10, 29, 0, 0, 0, 0, zoneId);
        Movie movie = new Movie("Saw", launchDate, Rating.R, new ArrayList<>(), new ArrayList<>());

        Assert.assertFalse(movie.isIndicatedForGeneralAudiences());
    }

}
