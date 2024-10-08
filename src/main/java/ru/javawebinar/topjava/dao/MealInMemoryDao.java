package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY_LIMIT;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealInMemoryDao implements MealDao {
    private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    private volatile int nextId = 1;

    @Override
    public void addMeal(LocalDateTime dateTime, String description, int calories) {
        int id = getNextId();
        meals.putIfAbsent(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(id);
    }

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        meals.replace(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(meals.values());
    }

    public List<MealTo> getAllMealsTo() {
        return filteredByStreams(getAllMeals(), LocalTime.MIN,
                LocalTime.MAX, CALORIES_PER_DAY_LIMIT);
    }

    @Override
    public Meal getMealById(int id) {
        return meals.get(id);
    }

    private synchronized int getNextId() {
        return nextId++;
    }
}
