package me.lucasmarques.popcorn.director.repository.impl;

import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
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

public class RelationalDatabaseDirectorRepository implements DirectorRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final RelationalDatabaseConnectionDriver driver;
    private final String databaseName = DatabaseName.DIRECTORS.value;

    public RelationalDatabaseDirectorRepository(RelationalDatabaseConnectionDriver driver) {
        this.driver = driver;
    }

    public Director save(Director director) {
        Director result = null;
        String sql = String.format("INSERT INTO %s (id, name) VALUES ('%s', '%s')",
                databaseName, UUID.randomUUID().toString(), director.getName());

        if (findByName(director.getName()) == null) {
            try {
                driver.executeSql(sql);
                result = director;
            } catch (SQLException e) {
                logger.error(String.format("Could not process insert: %s. Error: %s", sql, e.getMessage()));
            } finally {
                driver.close();
            }
        }

        return result;
    }

    public Director findByName(String name) {
        Director director = null;
        String query = String.format("SELECT * FROM %s WHERE name = '%s' LIMIT 1", databaseName, name);

        try {
            ResultSet result = driver.executeSql(query);
            while (result.next()) {
                director = buildDirector(result.getString("id"), result.getString("name"),
                        result.getString("created_at"), result.getString("updated_at"));
            }
        } catch (SQLException e) {
            logger.error(String.format("Could not process query: %s. Error: %s", query, e.getMessage()));
        } finally {
            driver.close();
        }

        return director;
    }

    public List<Director> findByMovieId(UUID movieId) {
        List<Director> directors = new ArrayList<>();

        String query = String.format("SELECT * FROM %s AS d "
                + "JOIN %s AS md "
                + "ON md.director_id = d.id "
                + "WHERE md.movie_id = '%s'",
                databaseName, DatabaseName.MOVIES_DIRECTORS.value, movieId.toString());

        try {
            ResultSet result = driver.executeSql(query);
            while (result.next()) {
                Director director = buildDirector(result.getString("id"), result.getString("name"),
                        result.getString("created_at"), result.getString("updated_at"));
                directors.add(director);
            }
        } catch (SQLException e) {
            logger.error(String.format("Could not process query: %s. Error: %s", query, e.getMessage()));
        } finally {
            driver.close();
        }

        return directors;
    }

    public Director buildDirector(String id, String name, String createdAt, String updatedAt) {
        UUID directorId = UUID.fromString(id);
        String actorName = StringCharsetConverter.convert(name, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
        ZonedDateTime actorCreatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(createdAt));
        ZonedDateTime actorUpdatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(updatedAt));
        return new Director(directorId, actorName, actorCreatedAt, actorUpdatedAt);
    }
}
