<%-- 
    Document   : listStaff
    Created on : Oct 18, 2024, 10:34:50 AM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff List</title>
        <script type="text/javascript">
            function doDelete(id) {
                if (confirm("Are you sure you want to delete staff with ID: " + id + "?")) {
                    window.location = "deleteStaff?id=" + id;
                }
            }
        </script>
        <link rel="stylesheet" href="css/list.css">
    </head>
    <body>
        <a href="admin.jsp" 
           style="font-size: 25px; font-weight: 500;">Home Admin</a>
        <h1>List of Staff</h1>
        <h3><a href="addAccountandStaff.jsp"> Add new staff </a></h3>
    <center>
        <table border="1">
            <thead>
                <tr>
                    <th>Staff ID</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Name</th>
                    <th>StaffRole</th>
                    <th>AccountRole</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${requestScope.listStaff}">
                    <tr>
                        <td>${staff.staffId}</td>
                        <td>${staff.account.username}</td>
                        <td>${staff.account.password}</td>
                        <td>${staff.staffName}</td>
                        <td>${staff.staffRole}</td> <!-- Staff Role từ bảng Staff -->
                        <td>${staff.account.role}</td> <!-- Account Role từ bảng Account -->

                        <td>
                            <a href="#" onclick="doDelete('${staff.staffId}')"> delete </a>
                            <a href="updateAccountandStaff?id=${staff.staffId}"> update </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </center>
    <c:if test="${not empty error}">
        <div style="color: red;">
            ${error}
        </div>
    </c:if>
</body>
</html>