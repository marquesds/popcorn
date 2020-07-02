package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryActorRepository implements ActorRepository {

    private List<Actor> initialData;
    private List<Movie> movies;
    private Map<Movie, Actor> relationship;

    public InMemoryActorRepository(List<Actor> initialData, List<Movie> movies, Map<Movie, Actor> relationship) {
        this.initialData = initialData;
        this.movies = movies;
        this.relationship = relationship;
    }

    public Actor save(Actor actor) {
        initialData.add(actor);
        return actor;
    }

    public Actor findByName(String name) {
        Actor foundActor = null;
        List<Actor> result = initialData
                .stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());

        if (result.size() > 0) {
            foundActor = result.get(0);
        }

        return foundActor;
    }

    public List<Actor> findByMovieId(UUID movieId) {
        List<Actor> actors = new ArrayList<>();
        List<Movie> moviesResult = movies
                .stream()
                .filter(m -> m.getId().equals(movieId))
                .collect(Collectors.toList());

        if (moviesResult.size() > 0) {
            Movie movie = moviesResult.get(0);
            actors.add(relationship.get(movie));
        }

        return actors;
    }
}
