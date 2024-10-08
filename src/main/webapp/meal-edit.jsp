<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <title>Edit meal</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<div>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">

        <input type="hidden" name="id" value="${meal.id}">

        <input type="datetime-local" name="datetime"
               placeholder="Дата и время приема пищи"
               size=10
               value="${meal.dateTime}" required>
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
