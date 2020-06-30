package me.lucasmarques.popcorn.infra.persistence.mariadb;

import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseConnectionDriver;

import java.sql.*;

public class ConnectionDriver implements RelationalDatabaseConnectionDriver {

    private Connection connection;

    public Connection getConnection() {
        try {
            SystemConfig config = SystemConfig.getInstance();
            String url = config.getEnvironment().equals("test") ? config.getTestDatabaseUrl() : config.getDatabaseUrl();
            String user = config.getDatabaseUser();
            String password = config.getDatabasePassword();

            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet executeSql(String sql) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
}
