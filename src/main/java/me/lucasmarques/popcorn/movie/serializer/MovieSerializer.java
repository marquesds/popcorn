package me.lucasmarques.popcorn.movie.serializer;

import com.google.gson.Gson;
import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.shared.date.formatter.DateFormatter;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MovieSerializer {

    public static String serialize(Movie movie) {
        Gson gson = new Gson();
        Map<String, Object> movieMap = new HashMap<>();

        movieMap.put("id", movie.getId());
        movieMap.put("name", movie.getName());
        movieMap.put("launchDate", DateFormatter.format(movie.getLaunchDate()));
        movieMap.put("rating", movie.getRating());
        movieMap.put("cast", movie.getCast().stream().map(Actor::toString).collect(Collectors.toList()));
        movieMap.put("directedBy", movie.getDirectedBy().stream().map(Director::toString).collect(Collectors.toList()));
        movieMap.put("createdAt", movie.getCreatedAt().toString());
        movieMap.put("updatedAt", movie.getCreatedAt().toString());

        return gson.toJson(movieMap);
    }
}
