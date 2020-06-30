package me.lucasmarques.popcorn.movie.model;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.shared.abstraction.Entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Movie extends Entity {

    private String name;
    private ZonedDateTime launchDate;
    private Rating rating;
    private List<Director> directedBy;
    private List<Actor> cast;

    public Movie(UUID id, String name, ZonedDateTime launchDate, Rating rating, List<Director> directedBy, List<Actor> cast,
                 ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.setId(id);
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
        this.directedBy = directedBy;
        this.cast = cast;
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public Movie(String name, ZonedDateTime launchDate, Rating rating, List<Director> directedBy, List<Actor> cast) {
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
        this.directedBy = directedBy;
        this.cast = cast;
    }

    public Boolean isIndicatedForGeneralAudiences() {
        return getRating().equals(Rating.G);
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getLaunchDate() {
        return launchDate;
    }

    public Rating getRating() {
        return rating;
    }

    public List<Director> getDirectedBy() {
        return directedBy;
    }

    public List<Actor> getCast() {
        return cast;
    }
}
