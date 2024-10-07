package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealInMemoryDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    MealDao meals;

    @Override
    public void init() throws ServletException {
        super.init();
        meals = new MealInMemoryDao();
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
        int id;
        String strId = request.getParameter("id");
        if (strId == null && ("delete".equals(action) || "update".equals(action))) {
            throw new IllegalArgumentException("id cannot be null");
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
}
