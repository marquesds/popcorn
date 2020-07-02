package me.lucasmarques.popcorn.api.v1;

import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.actor.repository.impl.RelationalDatabaseActorRepository;
import me.lucasmarques.popcorn.director.repository.DirectorRepository;
import me.lucasmarques.popcorn.director.repository.impl.RelationalDatabaseDirectorRepository;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import me.lucasmarques.popcorn.movie.exception.*;
import me.lucasmarques.popcorn.movie.model.Censorship;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.repository.MovieRepository;
import me.lucasmarques.popcorn.movie.repository.impl.RelationalDatabaseMovieRepository;
import me.lucasmarques.popcorn.movie.schema.MovieDeserializer;
import me.lucasmarques.popcorn.movie.schema.MovieSerializer;
import me.lucasmarques.popcorn.movie.service.MovieService;

import static spark.Spark.*;

public class Api {

    public static void main(String[] args) {

        SystemConfig config = SystemConfig.getInstance();
        int port = config.getEnvironment().equals("test") ? config.getTestServicePort() : config.getServicePort();

        ConnectionDriver connection = new ConnectionDriver();

        ActorRepository actorRepository = new RelationalDatabaseActorRepository(connection);
        DirectorRepository directorRepository = new RelationalDatabaseDirectorRepository(connection);
        MovieRepository movieRepository = new RelationalDatabaseMovieRepository(connection, directorRepository, actorRepository);

        MovieService service = new MovieService(movieRepository, directorRepository, actorRepository);

        port(port);

        path("/api", () -> {
            path("/v1", () -> {
                get("/movies", (req, res) -> {
                    res.type("application/json");
                    String censorship = req.queryMap().get("censura").value();

                    if (censorship != null) {
                        if (censorship.equals("CENSURADO")) {
                            res.status(200);
                            return MovieSerializer.serialize(service.findByCensorship(Censorship.CENSORED));
                        } else if (censorship.equals("SEM_CENSURA")) {
                            res.status(200);
                            return MovieSerializer.serialize(service.findByCensorship(Censorship.NOT_CENSORED));
                        }
                    }

                    res.status(404);
                    return "{\"message\":\"Not found any movie.\"}";
                });

                post("/movies", (req, res) -> {
                    res.type("application/json");

                    try {
                        Movie movie = MovieDeserializer.deserializer(req.body());
                        service.save(movie);
                    } catch (MovieAlreadyPersistedException | CastMissingException | DirectedByMissingException | InvalidBodyException | InvalidCastLengthException e) {
                        res.status(400);
                        return String.format("{\"message\":\"%s\"}", e.getMessage());
                    }

                    res.status(201);
                    return "{\"message\":\"Movie created.\"}";
                });
            });
        });
    }

}
