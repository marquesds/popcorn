package me.lucasmarques.popcorn.director.model;

import me.lucasmarques.popcorn.shared.abstraction.Entity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Director extends Entity {

    private String name;

    public Director(String name) {
        this.name = name;
    }

    public Director(UUID id, String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        setId(id);
        this.name = name;
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return this.name;
    }

}
