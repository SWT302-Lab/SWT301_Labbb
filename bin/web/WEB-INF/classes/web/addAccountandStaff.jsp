<%-- 
    Document   : addAccountandStaff
    Created on : Oct 19, 2024, 8:41:47 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="css/addStaff.css">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create new Staff and Account</title>
    </head>
    <body>
        <h1>Add New Staff and Account</h1>
        
        <form action="addAccountandStaff" method="get">
            <h2>Account Information</h2>
            
            <!-- Username input -->
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required><br>

            <!-- Password input -->
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <!-- Account role selection -->
            <label for="accountRole">Account Role:</label>
            <select id="accountRole" name="accountRole" required>
               
                <option value="cashier">Cashier</option>
                <option value="waiter">Waiter</option>
            </select><br>

            <h2>Staff Information</h2>
            
            <!-- Staff name input -->
            <label for="staffName">Staff Name:</label>
            <input type="text" id="staffName" name="staffName" required><br>

            <!-- Staff role selection -->
            <label for="staffRole">Staff Role:</label>
            <select id="staffRole" name="staffRole" required>
                <option value="manager">Manager</option>
                <option value="staff">Staff</option>
            </select><br>

            <!-- Submit button -->
            <button type="submit">Add Staff</button>
        </form>
        
        <!-- Error message display -->
        <c:if test="${not empty error}">
            <div style="color: red;">
                ${error}
            </div>
        </c:if>
    </body>
</html>
