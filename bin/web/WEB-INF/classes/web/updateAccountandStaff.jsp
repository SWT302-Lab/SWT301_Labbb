<%-- 
    Document   : updateAccountandStaff
    Created on : Oct 19, 2024, 8:42:07 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/addStaff.css">
        <title>update Staff and Account</title>
    </head>
    <body>
           <h1>Update Staff and Account Information</h1>
        
        <form action="updateAccountandStaff" method="post">
            <!-- StaffID hidden input (to identify which staff is being updated) -->
            <input type="hidden" id="staffId" name="staffId" value="${staff.staffId}">

            <h2>Account Information</h2>
            
            <!-- Username input -->
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${staff.account.username}" required><br>

            <!-- Password input -->
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${staff.account.password}" required><br>

            <!-- Account role selection -->
            <label for="accountRole">Account Role:</label>
            <select id="accountRole" name="accountRole" required> 
                <option value="cashier" ${staff.account.role == 'cashier' ? 'selected' : ''}>Cashier</option>
                <option value="waiter" ${staff.account.role == 'waiter' ? 'selected' : ''}>Waiter</option>
            </select><br>

            <h2>Staff Information</h2>
            
            <!-- Staff name input -->
            <label for="staffName">Staff Name:</label>
            <input type="text" id="staffName" name="staffName" value="${staff.staffName}" required><br>

            <!-- Staff role selection -->
            <label for="staffRole">Staff Role:</label>
            <select id="staffRole" name="staffRole" required>
                <option value="manager" ${staff.staffRole == 'manager' ? 'selected' : ''}>Manager</option>
                <option value="staff" ${staff.staffRole == 'staff' ? 'selected' : ''}>Staff</option>
            </select><br>

            <!-- Submit button -->
            <button type="submit">Update Staff</button>
        </form>
        
        <!-- Error message display -->
        <c:if test="${not empty error}">
            <div style="color: red;">
                ${error}
            </div>
        </c:if>
    </body>
</html>
