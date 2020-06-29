package me.lucasmarques.popcorn.infra.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.cdimascio.dotenv.DotEnvException;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

import java.time.ZoneId;

public class SystemConfig {

    private static SystemConfig instance = null;

    private static Config application;
    private static Config database;

    private SystemConfig() {
    }

    public static SystemConfig getInstance() {
        if (instance == null) {
            try {
                loadEnvironmentVariables();
            } catch (DotEnvException e) {
                System.out.println(e.getMessage());
            }

            setConfigVariables();
            instance = new SystemConfig();
        }

        return instance;
    }

    public ZoneId getTimezone() {
        return ZoneId.of(application.getString("timezone"));
    }

    public String getEnvironment() {
        return application.getString("environment");
    }

    public String getDatabaseUrl() {
        return database.getString("url");
    }

    public String getDatabaseUser() {
        return database.getString("user");
    }

    public String getDatabasePassword() {
        return database.getString("password");
    }

    private static void loadEnvironmentVariables() throws DotEnvException {
        Dotenv dotenv = Dotenv.load();
        for (DotenvEntry entry : dotenv.entries()) {
            java.lang.System.setProperty(entry.getKey(), entry.getValue());
        }
    }

    private static void setConfigVariables() {
        Config conf = ConfigFactory.load();

        application = conf.getConfig("application");
        database = conf.getConfig("database");
    }

}
