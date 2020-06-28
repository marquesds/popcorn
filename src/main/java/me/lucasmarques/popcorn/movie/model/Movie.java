package me.lucasmarques.popcorn.movie.model;

import me.lucasmarques.popcorn.shared.abstraction.Entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Movie extends Entity {

    private String name;
    private ZonedDateTime launchDate;
    private Rating rating;
    private List<Entity> directors;
    private List<Entity> cast;

    public Movie(UUID id, String name, ZonedDateTime launchDate, Rating rating, List<Entity> directors, List<Entity> cast,
                 ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.setId(id);
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
        this.directors = directors;
        this.cast = cast;
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }

    public Movie(String name, ZonedDateTime launchDate, Rating rating, List<Entity> directors, List<Entity> cast) {
        this.name = name;
        this.launchDate = launchDate;
        this.rating = rating;
        this.directors = directors;
        this.cast = cast;
    }

    public Boolean isIndicatedForGeneralAudiences() {
        return getRating().equals(Rating.G);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(ZonedDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<Entity> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Entity> directors) {
        this.directors = directors;
    }

    public List<Entity> getCast() {
        return cast;
    }

    public void setCast(List<Entity> cast) {
        this.cast = cast;
    }
}
