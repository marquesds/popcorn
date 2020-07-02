package me.lucasmarques.popcorn.shared.uuid.converter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDConverter {

    public static UUID fromBytes(byte[] uuid) {
        String stringUUID = new String(uuid, StandardCharsets.UTF_8);
        return fromString(stringUUID);
    }

    public static UUID fromString(String uuid) {
        return UUID.fromString(uuid);
    }

}
