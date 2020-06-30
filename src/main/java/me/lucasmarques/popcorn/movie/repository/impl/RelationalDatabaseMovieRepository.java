package me.lucasmarques.popcorn.movie.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseConnectionDriver;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RelationalDatabaseMovieRepository implements MovieRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final RelationalDatabaseConnectionDriver driver;

    public RelationalDatabaseMovieRepository(RelationalDatabaseConnectionDriver driver) {
        this.driver = driver;
    }

    public Movie save(Movie movie) {
        return null;
    }

    public Movie findByName(String name) {
        return null;
    }

    public Movie findById(UUID id) {
        return null;
    }

    public Map<Movie, List<Actor>> relateActorsToMovie(List<Actor> actors, Movie movie) {
        return null;
    }

    public Map<Movie, List<Director>> relateDirectorsToMovie(List<Director> directors, Movie movie) {
        return null;
    }

    public List<Movie> findByRating(Rating rating) {
        return null;
    }
}
