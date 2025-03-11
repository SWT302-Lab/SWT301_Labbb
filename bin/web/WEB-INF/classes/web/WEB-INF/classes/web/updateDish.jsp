<%-- 
    Document   : update
    Created on : Oct 13, 2024, 8:22:29 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Dish</title>
        <link rel="stylesheet" href="css/addStaff.css">
    </head>
    <body>
        <h1>Update a dish</h1>

        <!-- Lấy đối tượng dish từ request scope -->
        <c:set var="d" value="${requestScope.dish}"/>

        <form action="updateDish" method="post">
            <input type="hidden" name="id" value="${d.dishId}" /> 
            <!-- Tên món ăn -->
            Enter name: <input type="text" name="name" value="${d.dishName}" /> <br>

            <!-- Giá món ăn -->
            Enter price: <input type="text" name="price" value="${d.price}" /> <br>

            Enter status (yes/no): 
            <select name="status">
                <option value="yes" ${d.status == 'yes' ? 'selected' : ''}>Yes</option>
                <option value="no" ${d.status == 'no' ? 'selected' : ''}>No</option>
            </select>
            <br>
            Enter ImageLink : <input type="text" name="image" value="${d.image}" /> <br>
            <!-- Nút để cập nhật -->
            <input type="submit" value="Update" />
        </form>
        <c:if test="${not empty error}">
            <div style="color: red;">
                ${error}
            </div>
        </c:if>
    </body>
</html>
