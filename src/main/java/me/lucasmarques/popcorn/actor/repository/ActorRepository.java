package me.lucasmarques.popcorn.actor.repository;

import me.lucasmarques.popcorn.actor.model.Actor;

import java.util.List;
import java.util.UUID;

public interface ActorRepository {

    Actor save(Actor actor);
    List<Actor> findByMovieId(UUID movieId);

}
