package me.lucasmarques.popcorn.actor.repository.impl;

import me.lucasmarques.popcorn.actor.model.Actor;
import me.lucasmarques.popcorn.actor.repository.ActorRepository;
import me.lucasmarques.popcorn.infra.persistence.RelationalDatabaseDslProvider;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RelationalDatabaseActorRepository implements ActorRepository {

    private DSLContext dslContext;

    public RelationalDatabaseActorRepository(RelationalDatabaseDslProvider dslProvider) {
        this.dslContext = dslProvider.getDSL();
    }

    public Actor save(Actor actor) {
        return null;
    }

    public List<Actor> search(String name) {
        List<Actor> actors = new ArrayList<>();

        Result<Record> result = this.dslContext
                .select()
                .from("actors")
                .where("actors.name = ?", name)
                .limit(10)
                .fetch();

        for(Record record : result) {
            UUID id = (UUID) record.get("id");
            // Actor actor = new Actor();
        }
        return null;
    }
}
