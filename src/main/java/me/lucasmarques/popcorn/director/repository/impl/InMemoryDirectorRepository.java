package me.lucasmarques.popcorn.director.repository.impl;

import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
import me.lucasmarques.popcorn.movie.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryDirectorRepository implements DirectorRepository {

    private List<Movie> movies;
    private Map<Movie, Director> relationship;
    private List<Director> initialData;

    public InMemoryDirectorRepository(List<Director> initialData, List<Movie> movies, Map<Movie, Director> relationship) {
        this.initialData = initialData;
        this.movies = movies;
        this.relationship = relationship;
    }

    public Director save(Director director) {
        initialData.add(director);
        return director;
    }

    public Director findByName(String name) {
        Director foundDirector = null;
        List<Director> result = initialData
                .stream()
                .filter(a -> a.getName().equals(name))
                .collect(Collectors.toList());

        if (result.size() >= 1) {
            foundDirector = result.get(0);
        }

        return foundDirector;
    }

    public List<Director> findByMovieId(UUID movieId) {
        List<Director> directors = new ArrayList<>();
        List<Movie> moviesResult = movies
                .stream()
                .filter(m -> m.getId().equals(movieId))
                .collect(Collectors.toList());

        if (moviesResult.size() >= 1) {
            Movie movie = moviesResult.get(0);
            directors.add(relationship.get(movie));
        }

        return directors;
    }
}
