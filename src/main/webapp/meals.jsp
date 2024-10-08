<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список приемов пищи</title>
</head>
<body>
<div>
    <table class="list-table">
        <tr class="t-header">
            <th>Дата приема пищи</th>
            <th>Описание еды</th>
            <th>Кол-во калорий</th>
        </tr>
        <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
        <c:forEach items="${mealsTo}" var="mealTo">
            <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class=${mealTo.excess ? '"t-row-with-excess"' : '"t-row-without-excess"'}>
                <td>
                    <%=TimeUtil.format(mealTo.getDateTime())%>
                </td>
                <td>
                        ${mealTo.description}
                </td>
                <td>
                        ${mealTo.calories}
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
