package me.lucasmarques.popcorn.shared.date;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static String format(ZonedDateTime date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
    }

    public static String format(ZonedDateTime date, String schema) {
        return DateTimeFormatter.ofPattern(schema).format(date);
    }

}
