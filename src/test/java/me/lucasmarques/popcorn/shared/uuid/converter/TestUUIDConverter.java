package me.lucasmarques.popcorn.shared.uuid.converter;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TestUUIDConverter {

    @Test
    public void testCanConvertValidBytesToUUID() {
        String uuid = "547c8a87-43e5-4e8e-8a63-eb99ab74caae";
        byte[] bytesUUID = uuid.getBytes(StandardCharsets.UTF_8);

        UUID expected = UUIDConverter.fromBytes(bytesUUID);
        Assert.assertEquals(UUID.fromString(uuid), expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotConvertInvalidBytesToUUID() {
        UUIDConverter.fromBytes("".getBytes(StandardCharsets.UTF_8));
    }

}
