package me.lucasmarques.popcorn.shared.abstraction;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class Entity {
    private UUID id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = Objects.requireNonNullElseGet(createdAt, ZonedDateTime::now);
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = Objects.requireNonNullElseGet(updatedAt, ZonedDateTime::now);
    }
}
