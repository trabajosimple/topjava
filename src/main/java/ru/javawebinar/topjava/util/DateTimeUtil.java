package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenHalfOpen(LocalDateTime lt, LocalDateTime startDateTime,
                                            LocalDateTime endDateTime) {
        return (startDateTime == null || lt.compareTo(startDateTime) >= 0) && (endDateTime == null ||
                lt.compareTo(endDateTime) < 0);
    }
    public static LocalDateTime convertToDateTime(LocalDate date, LocalTime time){
        return date == null || time == null ? null : LocalDateTime.of(date, time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

