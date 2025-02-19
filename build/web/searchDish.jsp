<%-- 
    Document   : searchDish
    Created on : Oct 22, 2024, 11:17:05 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/search.css">
        <style>
            /* CSS cho nút Order */
            .btn {
                padding: 12px 20px; /* Kích thước nút */
                background-color: #007bff; /* Màu nền */
                color: white; /* Màu chữ */
                border: none; /* Bỏ viền */
                border-radius: 5px; /* Bo góc */
                cursor: pointer; /* Con trỏ chuột khi hover */
                transition: background-color 0.3s, transform 0.2s; /* Hiệu ứng chuyển màu và biến đổi */
                font-size: 16px; /* Kích thước chữ */
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Bóng đổ nhẹ */
            }

            .btn:hover {
                background-color: #0056b3; /* Màu nền khi hover */
                transform: translateY(-2px); /* Nâng nút lên khi hover */
            }

            .btn:focus {
                outline: none; /* Bỏ viền focus */
            }

            .btn:active {
                transform: translateY(1px); /* Hiệu ứng khi nhấn */
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Thay đổi bóng đổ khi nhấn */
            }
        </style>
        <title>Search Dish</title>
    </head>

    <body>
        <h1>Result for: ${param.query}</h1>
        <a href="dish?tableID=${sessionScope.tableID}" class="btn btn-secondary" style="font-size: 24px"  >Menu</a>
        <h2>You are ordering for table : ${sessionScope.tableID}</h2>
        <c:choose>
            <c:when test="${empty searchResults}">
                <p>Not found.</p>
            </c:when>
            <c:otherwise> 
                <table border="1">
                    <thead>
                        <tr>
                            <th>DishID</th>
                            <th>DishName</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Image</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach var="dish" items="${searchResults}">
                            <tr>
                                <td>${dish.dishId}</td>
                                <td>${dish.dishName}</td>
                                <td>${dish.price}</td>
                                <td>${dish.status}</td>
                                <td><img src="${dish.image}" alt="Hình ảnh" width="100"></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

    </body>
</html>
