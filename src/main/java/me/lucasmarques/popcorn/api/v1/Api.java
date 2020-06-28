package me.lucasmarques.popcorn.api.v1;

import com.google.gson.Gson;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {
        System.out.println(SystemConfig.getInstance().getDatabaseUrl());

        ZoneId zoneId = ZoneId.of("UTC");
        Gson gson = new Gson();
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        Movie movie = new Movie("Lion King", launchDate, Rating.G, new ArrayList<>(), new ArrayList<>());
        get("/hello", (req, res) -> gson.toJson(movie));
    }

}
