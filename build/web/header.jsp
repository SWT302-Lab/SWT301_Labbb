<%-- 
    Document   : header
    Created on : 12 thg 10, 2024, 15:47:42
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.5.0/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            .nav-item {
                margin-right: 60px;
            }
        </style>
        
    </head>
    <body>
        <!-- Navigation bar -->
        <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #FF416C;">
            <div class="container-fluid">
                <!-- Logo (replace with your image/logo if needed) -->
                <a class="navbar-brand" href="dish?tableID=${sessionScope.tableID}" style="font-size: 24px; font-weight: bold;">
                    <img src="https://quanngon138.com/upload/hinhanh/75521_logo_vi.jpg" alt="Logo" style="width: 60px; margin-right: 10px;"> Nhà Hàng ABC
                </a>

                <!-- Toggle button for mobile view -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    
                </button>

                <!-- Navigation links -->
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <!-- 'Menu' link -->
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="dish?tableID=${sessionScope.tableID}"
                               style="font-size: 25px; font-weight: 500;">Menu</a>
                        </li>
                         <form class="d-flex search-bar" action="searchDish" method="get">
                            <input class="form-control me-2" type="search" placeholder="Tìm kiếm món ăn" aria-label="Search" name="query">
                            <button class="btn btn-outline-light" type="submit">Search</button>
                        </form>

                        <!-- 'Order' link -->
                        <li class="nav-item">
                            <a class="nav-link active" aria-current='page'href="viewOrder?tableID=${sessionScope.tableID}" 
                               style="font-size: 25px; font-weight: 500;">Order</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout" 
                               style="font-size: 25px; font-weight: 500;">Log Out</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

            
    </body>
</html>
