<%-- 
    Document   : list
    Created on : Oct 13, 2024, 8:18:04 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dish List</title>
        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Are you sure you want to delete dish with ID: " + id + "?")) {
                    window.location = "deleteDish?id=" + id;
                }
            }
        </script>
        <link rel="stylesheet" href="css/list.css">
    </head>
    <body>
        <a href="admin.jsp" 
           style="font-size: 25px; font-weight: 500;">Home Admin</a>
        <h1>List of Dishes</h1>
        <h3><a href="createDish.jsp"> Add new dish </a></h3>
    <center>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th>ImageLink</th>
                    <th>Action</th>
                </tr>
            </thead>

            <c:forEach var="dish" items="${requestScope.listDish}" >
                <tr>
                    <td>${dish.dishId}</td>
                    <td>${dish.dishName}</td>
                    <td>${dish.price}</td>
                    <td>${dish.status}</td>
                    <td><img src="${dish.image}" width="200" height="" alt="alt"/></td>
                    <td>
                        <a href="#" onclick="doDelete('${dish.dishId}')"> delete </a>
                        <a href="updateDish?id=${dish.dishId}"> update </a>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </center>
    <c:if test="${not empty error}">
        <div style="color: red;">
            ${error}
        </div>
    </c:if>
</body>
</html>
