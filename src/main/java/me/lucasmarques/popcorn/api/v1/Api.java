package me.lucasmarques.popcorn.api.v1;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.serializer.MovieSerializer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static spark.Spark.get;

public class Api {

    public static void main(String[] args) {
        System.out.println(SystemConfig.getInstance().getDatabaseUrl());

        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Lucas Marques"));
        actors.add(new Actor("Larissa Cristina"));
        List<Director> directors = new ArrayList<>();
        directors.add(new Director("Quentin Tarantino"));
        Movie movie = new Movie(UUID.randomUUID(), "Lion King", launchDate, Rating.G, directors, actors, ZonedDateTime.now(), ZonedDateTime.now());
        get("/hello", (req, res) -> MovieSerializer.serialize(movie));
    }

}
