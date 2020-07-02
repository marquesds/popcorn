package me.lucasmarques.popcorn.infra.persistence;

public enum DatabaseName {
    MOVIES("movies"),
    ACTORS("actors"),
    DIRECTORS("directors"),
    MOVIES_ACTORS("movies_actors"),
    MOVIES_DIRECTORS("movies_directors");

    public final String value;

    private DatabaseName(String value) {
        this.value = value;
    }
}
