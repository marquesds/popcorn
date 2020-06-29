package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseConnectionDriver;
import me.lucasmarques.popcorn.shared.date.converter.ZonedDateTimeConverter;
import me.lucasmarques.popcorn.shared.text.converter.StringCharsetConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelationalDatabaseActorRepository implements ActorRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final RelationalDatabaseConnectionDriver driver;

    public RelationalDatabaseActorRepository(RelationalDatabaseConnectionDriver driver) {
        this.driver = driver;
    }

    public Actor save(Actor actor) {
        return null;
    }

    public List<Actor> findByMovieId(UUID movieId) {
        List<Actor> actors = new ArrayList<>();

        String query = "SELECT * FROM actors AS a "
                + "JOIN movies_actors AS ma "
                + "ON ma.actor_id = a.id "
                + "WHERE ma.movie_id = '" + movieId.toString() + "' "
                + "LIMIT 10";

        try {
            ResultSet result = executeQuery(query);
            while (result.next()) {
                Actor actor = buildActor(result.getString("id"), result.getString("name"),
                        result.getString("created_at"), result.getString("updated_at"));
                actors.add(actor);
            }
        } catch (SQLException e) {
        } finally {
            driver.close();
        }

        return actors;
    }

    private Actor buildActor(String id, String name, String createdAt, String updatedAt) {
        UUID actorId = UUID.fromString(id);
        String actorName = StringCharsetConverter.convert(name, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
        ZonedDateTime actorCreatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(createdAt));
        ZonedDateTime actorUpdatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(updatedAt));
        return new Actor(actorId, actorName, actorCreatedAt, actorUpdatedAt);
    }

    private ResultSet executeQuery(String query) {
        ResultSet result = null;

        try {
            PreparedStatement preparedStatement = driver.getConnection().prepareStatement(query);
            result = preparedStatement.executeQuery();
        } catch (SQLException e) {
        }
        return result;
    }
}
