package me.lucasmarques.popcorn.movie.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryMovieRepository implements MovieRepository {

    private final List<Movie> initialData;

    public InMemoryMovieRepository(List<Movie> initialData) {
        this.initialData = initialData;
    }

    public Movie save(Movie movie) {
        initialData.add(movie);
        return movie;
    }

    public Movie findByName(String name) {
        return initialData
                .stream()
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Movie findById(UUID id) {
        return initialData
                .stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Map<Movie, List<Actor>> relateActorsToMovie(List<Actor> actors, Movie movie) {
        Map<Movie, List<Actor>> relationship = new HashMap<>();
        relationship.put(movie, actors);
        return relationship;
    }

    public Map<Movie, List<Director>> relateDirectorsToMovie(List<Director> directors, Movie movie) {
        Map<Movie, List<Director>> relationship = new HashMap<>();
        relationship.put(movie, directors);
        return relationship;
    }

    public List<Movie> findByRating(Rating rating) {
        return initialData
                .stream()
                .filter(m -> m.getRating().equals(rating))
                .collect(Collectors.toList());
    }

    public List<Movie> findByNotEqualRating(Rating rating) {
        return initialData
                .stream()
                .filter(m -> !m.getRating().equals(rating))
                .collect(Collectors.toList());
    }
}
