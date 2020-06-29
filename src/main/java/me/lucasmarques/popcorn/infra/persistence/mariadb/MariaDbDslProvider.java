package me.lucasmarques.popcorn.infra.persistence.mariadb;

import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseDslProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;

public class MariaDbDslProvider implements RelationalDatabaseDslProvider {

    public DSLContext getDSL() {
        Connection connection = ConnectionDriver.getConnection();
        return DSL.using(connection, SQLDialect.MYSQL);
    }
}
