<%-- 
    Document   : listTable
    Created on : Oct 23, 2024, 10:12:57 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Table List</title>
        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Bạn có chắc chắn muốn xóa bàn với ID: " + id + "?")) {
                    window.location = "deleteTable?id=" + id;
                }
            }
        </script>
        <link rel="stylesheet" href="css/list.css">
    </head>
    <body>
         <a href="admin.jsp" 
           style="font-size: 25px; font-weight: 500;">Home Admin</a>
           <h1>List Table</h1> 
        <h3><a href="createTable.jsp"> Add new Table </a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>

            <c:forEach var="table" items="${requestScope.listTable}">
                <tr>
                    <td>${table.id}</td>
                    <td>${table.status}</td>
                    <td>
                        <a href="#" onclick="doDelete('${table.id}')"> delete </a>
                        <a href="updateTable?id=${table.id}"> update </a>
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
