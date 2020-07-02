package me.lucasmarques.popcorn.infra.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface RelationalDatabaseConnectionDriver {

    Connection getConnection();
    ResultSet executeSql(String sql) throws SQLException;
    void close();

}
