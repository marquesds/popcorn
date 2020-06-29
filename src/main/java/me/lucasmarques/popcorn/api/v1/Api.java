package me.lucasmarques.popcorn.api.v1;

import com.google.gson.Gson;
import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.mariadb.ConnectionDriver;
import me.lucasmarques.popcorn.movie.model.Movie;
import me.lucasmarques.popcorn.movie.model.Rating;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static spark.Spark.get;

public class Api {

    public static void main(String[] args) throws SQLException {
        System.out.println(SystemConfig.getInstance().getDatabaseUrl());

        Connection connection = ConnectionDriver.getConnection();
        DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

        Result<Record> result = dslContext.select().from("movies").fetch();

        for (Record r : result) {
            Timestamp t = (Timestamp) r.get("launch_date");
            t.toInstant();
            ZonedDateTime createdAt = ZonedDateTime.ofInstant(t.toInstant(), SystemConfig.getInstance().getTimezone());
            System.out.println(createdAt);
        }

        connection.close();

        ZoneId zoneId = ZoneId.of("UTC");
        Gson gson = new Gson();
        ZonedDateTime launchDate = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        Movie movie = new Movie("Lion King", launchDate, Rating.G, new ArrayList<>(), new ArrayList<>());
        get("/hello", (req, res) -> gson.toJson(movie));
    }

}
