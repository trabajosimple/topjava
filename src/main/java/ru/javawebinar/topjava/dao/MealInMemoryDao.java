package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY_LIMIT;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealInMemoryDao implements MealDao {
    static private final ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();
    static private volatile int nextId = 1;
    static {
        int id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак",
                500));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин",
                500));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " +
                "граничное " +
                "значение", 100));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак",
                1000));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        id = getNextId();
        meals.put(id, new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410));
    }
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
        return filteredByStreams(getAllMeals(), LocalTime.of(0, 0),
                LocalTime.of(23, 59), CALORIES_PER_DAY_LIMIT);
    }

    @Override
    public Meal getMealById(int id) {
        return meals.get(id);
    }

    private static synchronized int getNextId() {
        return nextId++;
    }
}
