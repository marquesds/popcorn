package me.lucasmarques.popcorn.shared.date.converter;

import me.lucasmarques.popcorn.infra.config.SystemConfig;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter {

    public static ZonedDateTime fromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter).atStartOfDay(SystemConfig.getInstance().getTimezone());
    }

    public static ZonedDateTime fromTimestamp(Timestamp timestamp) {
        return ZonedDateTime.ofInstant(timestamp.toInstant(), SystemConfig.getInstance().getTimezone());
    }

}
