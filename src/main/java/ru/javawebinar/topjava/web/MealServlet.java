package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    MealDao meals;

    @Override
    public void init() throws ServletException {
        super.init();
        meals = new MealInMemoryDao();
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500);
        meals.addMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            request.setAttribute("mealsTo", meals.getAllMealsTo());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        int id = Meal.EMPTY_ID;
        String strId = request.getParameter("id");
        if (strId == null) {
            if ("delete".equals(action) || "update".equals(action)) {
                throw new IllegalArgumentException("id cannot be null");
            }
        } else {
            id = Integer.parseInt(request.getParameter("id"));
        }
        switch (action) {
            case "delete":
                meals.deleteMeal(id);
                response.sendRedirect("meals");
                return;
            case "update":
                meal = meals.getMealById(id);
                break;
            case "add":
                meal = Meal.EMPTY;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("meal-edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = TimeUtil.format(request.getParameter("datetime"));
        if (id == Meal.EMPTY_ID) {
            meals.addMeal(dateTime, description, calories);
        } else {
            meals.updateMeal(id, dateTime, description, calories);
        }
        response.sendRedirect("/topjava/meals");
    }
}
