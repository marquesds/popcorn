package me.lucasmarques.popcorn.shared;

import me.lucasmarques.popcorn.infra.config.SystemConfig;
import me.lucasmarques.popcorn.shared.date.DateFormatter;
import org.junit.Assert;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestDateFormatter {

    private final ZoneId zoneId = SystemConfig.getInstance().getTimezone();

    @Test
    public void testCanFormatValidDate() {
        ZonedDateTime date = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        Assert.assertEquals("2019-07-09", DateFormatter.format(date));
    }

    @Test
    public void testCanFormatValidDateWithCustomSchema() {
        String schema = "dd-MM-yyyy";
        ZonedDateTime date = ZonedDateTime.of(2019, 7, 9, 0, 0, 0, 0, zoneId);
        Assert.assertEquals("09-07-2019", DateFormatter.format(date, schema));
    }

    @Test(expected = DateTimeException.class)
    public void testCannotFormatInvalidValidDate() {
        ZonedDateTime date = ZonedDateTime.of(2019, 15, 9, 0, 0, 0, 0, zoneId);
        DateFormatter.format(date);
    }

    @Test(expected = DateTimeException.class)
    public void testCannotFormatInvalidValidDateWithCustomSchema() {
        String schema = "dd-MM-yyyy";
        ZonedDateTime date = ZonedDateTime.of(2019, 15, 9, 0, 0, 0, 0, zoneId);
        DateFormatter.format(date, schema);
    }

}
