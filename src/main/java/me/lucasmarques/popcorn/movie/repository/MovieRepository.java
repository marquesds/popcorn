package me.lucasmarques.popcorn.movie.repository;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MovieRepository {

    Movie save(Movie movie);
    Movie findByName(String name);
    Movie findById(UUID id);
    Map<Movie, List<Actor>> relateActorsToMovie(List<Actor> actors, Movie movie);
    Map<Movie, List<Director>> relateDirectorsToMovie(List<Director> directors, Movie movie);
    List<Movie> findByRating(Rating rating);

}
