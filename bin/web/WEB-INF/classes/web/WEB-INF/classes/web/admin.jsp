<%-- 
    Document   : admin
    Created on : 13 thg 10, 2024, 20:14:23
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/admin.css">
        <title>Admin Page</title>
    </head>
    <body> 
        <!-- Header -->
        <h1>Welcome to the Admin Page</h1>
        
        <!-- Log Out Link -->
        <a class="nav-link" href="logout">Log Out</a>
        
       
        
        <!-- Content Section with Cards -->
        <div id="content">
            <div class="card">
                <h2>Dish Manager</h2>
                <p>Manage restaurant dishes, prices, and availability.</p>
                <a href="getDish" class="nav-link">Go to Dish Manager</a>
            </div>
            <div class="card">
                <h2>Staff Manager</h2>
                <p>Manage staff details, roles, and schedules.</p>
                <a href="getStaff" class="nav-link">Go to Staff Manager</a>
            </div>
            <div class="card">
                <h2>Table Manager</h2>
                <p>Manage table arrangements, reservations, and occupancy.</p>
                <a href="getTable" class="nav-link">Go to Table Manager</a>
            </div>
        </div>
        
        <!-- Footer Section -->
        <footer>
            © 2024 Admin Dashboard. All rights reserved.
        </footer>
    </body>
</html>
