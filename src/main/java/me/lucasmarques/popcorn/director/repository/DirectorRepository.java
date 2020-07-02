package me.lucasmarques.popcorn.director.repository;

import me.lucasmarques.popcorn.director.model.Director;

import java.util.List;
import java.util.UUID;

public interface DirectorRepository {

    Director save(Director director);
    Director findByName(String name);
    List<Director> findByMovieId(UUID movieId);

}
