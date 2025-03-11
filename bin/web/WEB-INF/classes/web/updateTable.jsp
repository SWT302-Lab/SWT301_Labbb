<%-- 
    Document   : updateTable
    Created on : Oct 23, 2024, 10:20:56 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" href="css/addStaff.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Table</title>
    </head>
    <body>
        <h1>Update a Table</h1>

        <!-- Lấy đối tượng table từ request scope -->
        <c:set var="t" value="${requestScope.table}"/>

        <form action="updateTable" method="post">
            <input type="hidden" name="id" value="${t.id}" /> 
            Enter status (available/occupied): 
            <select name="status">
                <option value="available" ${t.status == 'available' ? 'selected' : ''}>Available</option>
                <option value="occupied" ${t.status == 'occupied' ? 'selected' : ''}>Occupied</option>
            </select>
            <br>
            <input type="submit" value="Update" />
        </form>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty error}">
            <div style="color: red;">
                ${error}
            </div>
        </c:if>
    </body>
</html>
