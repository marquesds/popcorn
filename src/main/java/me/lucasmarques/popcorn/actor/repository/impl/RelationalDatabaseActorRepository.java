package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.infra.persistence.DatabaseName;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseConnectionDriver;
import me.lucasmarques.popcorn.shared.date.converter.ZonedDateTimeConverter;
import me.lucasmarques.popcorn.shared.text.converter.StringCharsetConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelationalDatabaseActorRepository implements ActorRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final RelationalDatabaseConnectionDriver driver;
    private final String databaseName = DatabaseName.ACTORS.value;

    public RelationalDatabaseActorRepository(RelationalDatabaseConnectionDriver driver) {
        this.driver = driver;
    }

    public Actor save(Actor actor) {
        Actor result = null;
        String sql = String.format("INSERT INTO %s (id, name) VALUES ('%s', '%s')",
                databaseName, UUID.randomUUID(), actor.getName());

        if (findByName(actor.getName()) == null) {
            try {
                driver.executeSql(sql);
                result = actor;
            } catch (SQLException e) {
                logger.error(String.format("Could not process insert: %s. Error: %s", sql, e.getMessage()));
            } finally {
                driver.close();
            }
        }

        return result;
    }

    public Actor findByName(String name) {
        Actor actor = null;
        String query = String.format("SELECT * FROM %s WHERE name = '%s' LIMIT 1", databaseName, name);

        try {
            ResultSet result = driver.executeSql(query);
            while (result.next()) {
                actor = buildActor(result.getString("id"), result.getString("name"),
                        result.getString("created_at"), result.getString("updated_at"));
            }
        } catch (SQLException e) {
            logger.error(String.format("Could not process query: %s. Error: %s", query, e.getMessage()));
        } finally {
            driver.close();
        }

        return actor;
    }

    public List<Actor> findByMovieId(UUID movieId) {
        List<Actor> actors = new ArrayList<>();
        String query = String.format("SELECT * FROM %s AS a "
                + "JOIN %s AS ma "
                + "ON ma.actor_id = a.id "
                + "WHERE ma.movie_id = '%s' "
                + "LIMIT 10",
                databaseName, DatabaseName.MOVIES_ACTORS.value, movieId);

        try {
            ResultSet result = driver.executeSql(query);
            while (result.next()) {
                Actor actor = buildActor(result.getString("id"), result.getString("name"),
                        result.getString("created_at"), result.getString("updated_at"));
                actors.add(actor);
            }
        } catch (SQLException e) {
            logger.error(String.format("Could not process query: %s. Error: %s", query, e.getMessage()));
        } finally {
            driver.close();
        }

        return actors;
    }

    public Actor buildActor(String id, String name, String createdAt, String updatedAt) {
        UUID actorId = UUID.fromString(id);
        String actorName = StringCharsetConverter.convert(name, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
        ZonedDateTime actorCreatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(createdAt));
        ZonedDateTime actorUpdatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(updatedAt));
        return new Actor(actorId, actorName, actorCreatedAt, actorUpdatedAt);
    }
}
