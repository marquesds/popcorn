package me.lucasmarques.popcorn.movie.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
import me.lucasmarques.popcorn.infra.persistence.DatabaseName;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseConnectionDriver;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;
import me.lucasmarques.popcorn.shared.date.converter.ZonedDateTimeConverter;
import me.lucasmarques.popcorn.shared.text.converter.StringCharsetConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;

public class RelationalDatabaseMovieRepository implements MovieRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final RelationalDatabaseConnectionDriver driver;

    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final String databaseName = DatabaseName.MOVIES.value;

    public RelationalDatabaseMovieRepository(RelationalDatabaseConnectionDriver driver, DirectorRepository directorRepository,
                                             ActorRepository actorRepository) {
        this.driver = driver;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
    }

    public Movie save(Movie movie) {
        Movie result = null;
        String sql = String.format("INSERT INTO %s (id, name, launch_date, rating) VALUES ('%s', '%s', %s, '%s')",
                databaseName, UUID.randomUUID(), movie.getName(),
                Timestamp.valueOf(movie.getLaunchDate().toLocalDateTime()), movie.getRating());

        if (findByName(movie.getName()) == null) {
            try {
                driver.executeSql(sql);
                result = movie;
            } catch (SQLException e) {
                logger.error(String.format("Could not process insert: %s. Error: %s", sql, e.getMessage()));
            } finally {
                driver.close();
            }
        }

        return result;
    }

    public Movie findByName(String name) {
        String query = String.format("SELECT * FROM %s WHERE name = '%s' LIMIT 1", databaseName, name);
        return getMovie(query);
    }

    public Movie findById(UUID id) {
        String query = String.format("SELECT * FROM %s WHERE id = '%s' LIMIT 1", databaseName, id);
        return getMovie(query);
    }

    public Map<Movie, List<Actor>> relateActorsToMovie(List<Actor> actors, Movie movie) {
        Map<Movie, List<Actor>> result = new HashMap<>();
        List<Actor> savedActors = new ArrayList<>();

        for (Actor actor : actors) {
            String sql = String.format("INSERT INTO %s (movie_id, actor_id) VALUES ('%s', '%s')",
                    DatabaseName.MOVIES_ACTORS.value, movie.getId(), actor.getId());
            try {
                driver.executeSql(sql);
                savedActors.add(actor);
            } catch (SQLException e) {
                logger.error(String.format("Could not process insert: %s. Error: %s", sql, e.getMessage()));
            }
        }

        result.put(movie, savedActors);
        return result;
    }

    public Map<Movie, List<Director>> relateDirectorsToMovie(List<Director> directors, Movie movie) {
        Map<Movie, List<Director>> result = new HashMap<>();
        List<Director> savedDirectors = new ArrayList<>();

        for (Director director : directors) {
            String sql = String.format("INSERT INTO %s (movie_id, director_id) VALUES ('%s', '%s')",
                    DatabaseName.MOVIES_DIRECTORS.value, movie.getId(), director.getId());
            try {
                driver.executeSql(sql);
                savedDirectors.add(director);
            } catch (SQLException e) {
                logger.error(String.format("Could not process insert: %s. Error: %s", sql, e.getMessage()));
            } finally {
                driver.close();
            }
        }

        result.put(movie, savedDirectors);
        return result;
    }

    public List<Movie> findByRating(Rating rating) {
        String query = String.format("SELECT * FROM %s WHERE rating = '%s'", databaseName, rating);
        return getMovies(query);
    }

    public List<Movie> findByNotEqualRating(Rating rating) {
        String query = String.format("SELECT * FROM %s WHERE rating != '%s'", databaseName, rating);
        return getMovies(query);
    }

    public Movie buildMovie(String id, String name, String launchDate, String rating, String createdAt, String updatedAt) {
        UUID movieId = UUID.fromString(id);
        String movieName = StringCharsetConverter.convert(name, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
        ZonedDateTime movieLaunchDate = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(launchDate));
        Rating movieRating = Rating.valueOf(rating);
        ZonedDateTime movieCreatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(createdAt));
        ZonedDateTime movieUpdatedAt = ZonedDateTimeConverter.fromTimestamp(Timestamp.valueOf(updatedAt));

        List<Director> directors = directorRepository.findByMovieId(movieId);
        List<Actor> actors = actorRepository.findByMovieId(movieId);

        return new Movie(movieId, movieName, movieLaunchDate, movieRating, directors, actors, movieCreatedAt, movieUpdatedAt);
    }

    private Movie getMovie(String query) {
        List<Movie> movies = getMovies(query);
        Movie movie = null;

        if (movies.size() > 0) {
            movie = movies.get(0);
        }

        return movie;
    }

    private List<Movie> getMovies(String query) {
        List<Movie> movies = new ArrayList<>();

        try {
            ResultSet result = driver.executeSql(query);
            while (result.next()) {
                Movie movie = buildMovie(result.getString("id"), result.getString("name"),
                        result.getString("launch_date"), result.getString("rating"),
                        result.getString("created_at"), result.getString("updated_at"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            logger.error(String.format("Could not process query: %s. Error: %s", query, e.getMessage()));
        } finally {
            driver.close();
        }

        return movies;
    }
}
