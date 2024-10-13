package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, Integer userId);

    // false if meal does not belong to userId
    boolean delete(Integer id, Integer userId);

    // null if meal does not belong to userId
    Meal get(Integer id, Integer userId);

    // ORDERED dateTime desc
    Collection<Meal> getAll();

    Collection<Meal> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate,
                            LocalTime endTime);
}
