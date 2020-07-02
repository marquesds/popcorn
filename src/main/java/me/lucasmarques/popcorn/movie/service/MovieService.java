package me.lucasmarques.popcorn.movie.service;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.director.model.Director;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
import me.lucasmarques.popcorn.movie.exception.CastMissingException;
import me.lucasmarques.popcorn.movie.exception.DirectedByMissingException;
import me.lucasmarques.popcorn.movie.exception.MovieAlreadyPersistedException;
import me.lucasmarques.popcorn.movie.model.Censorship;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;

    public MovieService(MovieRepository movieRepository, DirectorRepository directorRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
    }

    public void save(Movie movie) throws MovieAlreadyPersistedException, CastMissingException, DirectedByMissingException {
        validateMovie(movie);

        movieRepository.save(movie);
        List<Director> directors = saveDirectors(movie.getDirectedBy());
        List<Actor> actors = saveActors(movie.getCast());

        movie = movieRepository.findByName(movie.getName());
        movieRepository.relateActorsToMovie(actors, movie);
        movieRepository.relateDirectorsToMovie(directors, movie);

        logger.info(String.format("The movie %s was persisted on database.", movie.getName()));
    }

    public List<Movie> findByCensorship(Censorship censorship) {
        List<Movie> movies;

        if (censorship.equals(Censorship.CENSORED)) {
            movies = movieRepository.findByNotEqualRating(Rating.G);
        } else {
            movies = movieRepository.findByRating(Rating.G);
        }

        return movies;
    }

    private List<Director> saveDirectors(List<Director> directors) {
        List<Director> result = new ArrayList<>();
        for (Director director : directors) {
            directorRepository.save(director);
            result.add(directorRepository.findByName(director.getName()));
        }
        return result;
    }

    private List<Actor> saveActors(List<Actor> actors) {
        List<Actor> result = new ArrayList<>();
        for (Actor actor : actors) {
            actorRepository.save(actor);
            result.add(actorRepository.findByName(actor.getName()));
        }
        return result;
    }

    private void validateMovie(Movie movie) throws CastMissingException, DirectedByMissingException, MovieAlreadyPersistedException {
        if (isMoviePersisted(movie)) {
            String errorMessage = String.format("The movie %s is already persisted on database.", movie.getName());
            logger.error(errorMessage);
            throw new MovieAlreadyPersistedException(errorMessage);
        }

        if (movie.getCast().isEmpty()) {
            String errorMessage = String.format("The movie %s dont have a cast.", movie.getName());
            logger.error(errorMessage);
            throw new CastMissingException(errorMessage);
        }

        if (movie.getDirectedBy().isEmpty()) {
            String errorMessage = String.format("The movie %s dont have any director.", movie.getName());
            logger.error(errorMessage);
            throw new DirectedByMissingException(errorMessage);
        }
    }

    private Boolean isMoviePersisted(Movie movie) {
        return movieRepository.findByName(movie.getName()) != null;
    }

}
