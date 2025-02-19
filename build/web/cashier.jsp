<%-- 
    Document   : cashier
    Created on : 12 thg 10, 2024, 15:21:00
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/cashier.css">
        <title>JSP Page</title>
    </head>
    <body>
      
        <div class="container mt-5">
              <a class="btn btn-danger btn-logout" href="logout">Log Out</a> <!-- Thay đổi đây -->
            <div class="number-grid mt-4">
            <div class="number-grid">
                <div class="row justify-content-start">
                    <c:forEach var="i" items="${requestScope.tableList}">
                        <c:set var="id" value="${i.id}"></c:set>
                        <c:set var="status" value="${i.status}"></c:set>
                            <div class="col-auto">
                                <a href="manage?tableID=${id}" class="number-box">
                                    <span>${id}</span>
                                <c:choose>
                                    <c:when test="${status == 'available'}">
                                        <span class="dot green"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="dot pink"></span>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
