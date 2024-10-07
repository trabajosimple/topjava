<%--
  Created by IntelliJ IDEA.
  User: Dmitry Zaitsev
  Date: 10/6/2024
  Time: 3:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список приемов пищи</title>
    <a href="meals?action=add">
        Add meal
    </a>
</head>
<body>
<div>
    <table class="list-table">
        <tr class="t-header">
            <th>Дата приема пищи</th>
            <th>Описание еды</th>
            <th>Кол-во калорий</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsTo}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr     <c:if test="${mealTo.excess}">  class="t-row-with-excess"    </c:if>
                    <c:if test="${!mealTo.excess}"> class="t-row-without-excess" </c:if>>
                <td>
                        ${mealTo.date}
                </td>
                <td>
                        ${mealTo.description}
                </td>
                <td>
                        ${mealTo.calories}
                </td>
                <td>
                    <a href="meals?id=${mealTo.id}&action=update">
                        Update
                    </a>
                </td>
                <td>
                    <a href="meals?id=${mealTo.id}&action=delete">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
