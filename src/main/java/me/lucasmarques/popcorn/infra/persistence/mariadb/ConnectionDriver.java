package me.lucasmarques.popcorn.infra.persistence.mariadb;

import me.lucasmarques.popcorn.infra.config.SystemConfig;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionDriver {
    public static Connection getConnection() {
        try {
            SystemConfig config = SystemConfig.getInstance();
            String url = config.getDatabaseUrl();
            String user = config.getDatabaseUser();
            String password = config.getDatabasePassword();
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
