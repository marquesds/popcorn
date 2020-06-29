package me.lucasmarques.popcorn.actor.repository;

import me.lucasmarques.popcorn.actor.model.Actor;

import java.util.List;

public interface ActorRepository {

    Actor save(Actor actor);
    List<Actor> search(String name);

}
