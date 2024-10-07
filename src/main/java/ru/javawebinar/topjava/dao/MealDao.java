package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDao {

    public void addMeal(LocalDateTime dateTime, String description, int calories);

    public void deleteMeal(int id);

    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories);

    public List<Meal> getAllMeals();

    public Meal getMealById(int id);
    List<MealTo> getAllMealsTo();
}
