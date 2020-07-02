package me.lucasmarques.popcorn.movie.schema;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.movie.exception.InvalidBodyException;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.shared.date.converter.ZonedDateTimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDeserializer {

    private static final Logger logger = LoggerFactory.getLogger("MovieDeserializer");

    public static Movie deserializer(String jsonString) throws InvalidBodyException {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(jsonString, JsonObject.class);

        List<Director> directedBy = new ArrayList<>();
        List<Actor> cast = new ArrayList<>();

        try {
            String name = json.get("name").getAsString();
            ZonedDateTime launchDate = ZonedDateTimeConverter.fromString(json.get("launch_date").getAsString());
            Rating rating = Rating.valueOf(json.get("rating").getAsString());

            json.get("directed_by").getAsJsonArray().forEach(e -> directedBy.add(new Director(e.getAsString())));
            json.get("cast").getAsJsonArray().forEach(e -> cast.add(new Actor(e.getAsString())));

            return new Movie(name, launchDate, rating, directedBy, cast);
        } catch (Exception e) {
            String errorMessage = String.format("Invalid body: %s", jsonString);
            logger.error(errorMessage);
            throw new InvalidBodyException(errorMessage);
        }
    }
}
