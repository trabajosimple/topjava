package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm");
//    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime
//    endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
//    }

    public static boolean isMealBetweenHalfOpenForCurrentUser(Meal meal,
                                                              LocalDate startDate,
                                                              LocalTime startTime,
                                                              LocalDate endDate,
                                                              LocalTime endTime,
                                                              int userId) {
        return (startDate == null || meal.getDate().compareTo(startDate) >= 0)
                && (endDate == null || meal.getDate().compareTo(endDate) <= 0)
                && (startTime == null || meal.getTime().compareTo(startTime) >= 0)
                && (endTime == null || meal.getTime().compareTo(endTime) <= 0)
                && meal.getUserId() == userId;
    }

    public static LocalDateTime convertToDateTime(LocalDate date, LocalTime time) {
        return date == null || time == null ? null : LocalDateTime.of(date, time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

