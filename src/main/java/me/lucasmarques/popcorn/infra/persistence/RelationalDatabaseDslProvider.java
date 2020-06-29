package me.lucasmarques.popcorn.infra.persistence;

import org.jooq.DSLContext;

public interface RelationalDatabaseDslProvider {

    DSLContext getDSL();

}
