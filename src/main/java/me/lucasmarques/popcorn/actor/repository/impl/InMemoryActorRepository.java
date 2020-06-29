package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;

import java.util.List;
import java.util.UUID;

public class InMemoryActorRepository implements ActorRepository {

    private List<Actor> initialData;

    public InMemoryActorRepository(List<Actor> initialData) {
        this.initialData = initialData;
    }

    public Actor save(Actor actor) {
        return null;
    }

    public List<Actor> findByMovieId(UUID movieId) {
        return null;
    }
}
