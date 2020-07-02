package me.lucasmarques.popcorn.movie.exception;

public class MovieAlreadyPersistedException extends Exception {
    public MovieAlreadyPersistedException(String message) {
        super(message);
    }
}
