package me.lucasmarques.popcorn.shared.date.converter;

import me.lucasmarques.popcorn.infra.config.SystemConfig;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class TestZonedDateTimeConverter {

    private final ZoneId zoneId = SystemConfig.getInstance().getTimezone();

    @Test
    public void testCanConvertValidStringDateToZonedDateTime() {
        ZonedDateTime expected = ZonedDateTime.of(2019, 10, 20, 0, 0, 0, 0, zoneId);
        ZonedDateTime result = ZonedDateTimeConverter.fromString("2019-10-20");

        Assert.assertEquals(result, expected);
    }

    @Test(expected = DateTimeParseException.class)
    public void testCannotConvertInvalidStringDateToZonedDateTime() {
        ZonedDateTimeConverter.fromString("2019-15-20");
    }

    @Test
    public void testCanConvertValidTimestampToZonedDateTime() {
        ZonedDateTime date = ZonedDateTime.of(2019, 10, 20, 0, 0, 0, 0, zoneId);
        Timestamp timestamp = Timestamp.from(date.toInstant());

        ZonedDateTime expected = ZonedDateTime.of(2019, 10, 20, 0, 0, 0, 0, zoneId);
        ZonedDateTime result = ZonedDateTimeConverter.fromTimestamp(timestamp);

        Assert.assertEquals(result, expected);
    }

}
