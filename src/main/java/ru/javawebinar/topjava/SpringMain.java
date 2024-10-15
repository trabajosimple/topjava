package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring" +
                "/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password",
                    2000, Role.ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAll());
            MealRepository mealRepository = appCtx.getBean(InMemoryMealRepository.class);
            Meal meal = mealRepository.get(1, 1);
            System.out.println(mealRepository.save(meal, 2));
            System.out.println(mealRepository.save(meal, 1));
            List<Meal> meals = mealRepository.getAll(
                    LocalDate.of(2020, 1, 1),
                    LocalTime.of(10,0),
                    LocalDate.of(2020, 2, 1),
                    LocalTime.of(14,0),
                    1);
            System.out.println(meals);
            System.out.println(mealRepository.getAll(
                    null,
                    null,
                    null,
                    null,
                    1));

        }
    }
}
