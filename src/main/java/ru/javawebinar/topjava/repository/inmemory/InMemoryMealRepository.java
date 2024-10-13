package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private UserRepository userRepository;

    @Autowired
    public InMemoryMealRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        setRepositoryByDefault();
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        // null if updated meal does not belong to userId
        Meal mealNew;
        try {
            return repository.computeIfPresent(meal.getId(),
                    (id, oldMeal) -> {
                        if (Objects.equals(oldMeal.getUserId(), userId)) return meal;
                        else throw new IllegalArgumentException("");
                    });
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // false if meal does not belong to userId
    @Override
    public boolean delete(Integer id, Integer userId) {
        return repository.entrySet()
                .removeIf(element -> {
                    Meal meal = element.getValue();
                    return meal.getUserId().equals(userId) && meal.getId().equals(id);
                });
    }

    // null if meal does not belong to userId
    @Override
    public Meal get(Integer id, Integer userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate,
                                   LocalTime endTime) {
        return repository.values()
                .stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(),
                        DateTimeUtil.convertToDateTime(startDate, startTime),
                        DateTimeUtil.convertToDateTime(endDate, endTime)))
                .sorted((v1, v2) -> v2.getDateTime().compareTo(v1.getDateTime()))
                .toList();
    }

    @Override
    public Collection<Meal> getAll() {
        return getAll(null, null, null, null);
    }


    private void setRepositoryByDefault() {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное " +
                        "значение", 100),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                userRepository.getByEmail("petrov@mailw.ru").getId());
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин",
                        410),
                userRepository.getByEmail("petrov@mailw.ru").getId());
    }
}

