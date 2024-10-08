package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {

    public static final Meal EMPTY = new Meal();
    public static final int EMPTY_ID = -1;
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(int id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal() {
        this.id = EMPTY_ID;
        this.dateTime = LocalDateTime.now();
        this.description = "Укажите прием пищи";
        this.calories = 0;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public int getId() {
        return id;
    }
}
