package me.lucasmarques.popcorn.actor.model;

import me.lucasmarques.popcorn.shared.abstraction.Entity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Actor extends Entity {

    private String name;

    public Actor(String name) {
        this.name = name;
    }

    public Actor(UUID id, String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        setId(id);
        this.name = name;
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
