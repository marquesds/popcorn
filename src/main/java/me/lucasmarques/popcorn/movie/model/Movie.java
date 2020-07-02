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

    public Movie(String name, ZonedDateTime launchDate, Rating rating) {
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
    }

    public Boolean isIndicatedForGeneralAudiences() {
        return getRating().equals(Rating.G);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLaunchDate(ZonedDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public ZonedDateTime getLaunchDate() {
        return launchDate;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }

    public void setDirectedBy(List<Director> directedBy) {
        this.directedBy = directedBy;
    }

    public List<Director> getDirectedBy() {
        return directedBy;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }

    public List<Actor> getCast() {
        return cast;
    }
}
