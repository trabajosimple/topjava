<%--
  Created by IntelliJ IDEA.
  User: Dmitry Zaitsev
  Date: 10/6/2024
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<div>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">

        <input type="datetime-local" name="dateTime"
               placeholder="Дата и время приема пищи"
               size=10
               value="${meal.dateTime}">
        <br>
        <input type="text" name="description" size=55 placeholder="Описание приема пищи" value="${meal.description}"
               required>
        <br>
        <input type="number" name="calories" size=55 placeholder="Кол-во калорий" value="${meal.calories}" required>
        <br>
        <div class="button-section">
            <button type="button" onclick="window.history.back()">Отменить</button>
            <button type="submit">Сохранить</button>
        </div>
    </form>

</div>

</body>
</html>
