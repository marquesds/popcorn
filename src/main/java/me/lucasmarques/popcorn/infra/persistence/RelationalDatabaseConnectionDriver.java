package me.lucasmarques.popcorn.infra.persistence;

import java.sql.Connection;

public interface RelationalDatabaseConnectionDriver {

    Connection getConnection();
    void close();

}
