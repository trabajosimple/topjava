package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        setRepositoryByDefault();
    }

    private void setRepositoryByDefault() {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0), "Завтрак", 500),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 13, 0), "Обед", 1000),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 20, 0), "Ужин", 500),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 30), "Еда на граничное " +
                        "значение", 100),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 2, 10, 0), "Завтрак", 1000),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 3, 13, 0), "Обед", 500),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 3, 20, 0), "Ужин",
                        410),
                1);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 11, 0), "Завтрак", 550),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 2, 14, 0), "Обед", 1050),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 3, 21, 0), "Ужин", 550),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 1, 1, 0), "Еда на граничное " +
                        "значение", 150),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 2, 11, 0), "Завтрак", 1500),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 3, 14, 0), "Обед", 550),
                2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 3, 21, 0), "Ужин",
                        450),
                2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            log.info("insert {}", meal);
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        // null if updated meal does not belong to userId
        log.info("update {}", meal);
        final char[] result = {'S'};
        repository.computeIfPresent(meal.getId(),
                (id, oldMeal) -> {
                    if (Objects.equals(oldMeal.getUserId(), userId)) {
                        return meal;
                    } else {
                        result[0] = 'E';
                        return oldMeal;
                    }
                });
        return result[0] == 'S' ? meal : null;
    }

    // false if meal does not belong to userId
    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} {}", id, userId);
        return repository.values()
                .removeIf(element ->
                        element.getUserId().equals(userId) && element.getId().equals(id));
    }

    // null if meal does not belong to userId
    @Override
    public Meal get(int id, int userId) {
        log.info("get {} {}", id, userId);
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate,
                             LocalTime endTime, int userId) {
        log.info("getAll {} {} {} {} {}", startDate, startTime, endDate, endTime, userId);
        return repository.values()
                .stream()
                .filter(meal -> DateTimeUtil.isMealBetweenHalfOpenForCurrentUser(meal,
                        startDate, startTime,
                        endDate, endTime, userId))
                .sorted((v1, v2) -> v2.getDateTime().compareTo(v1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAll(null, null, null, null, userId);
    }
}

