<%-- 
    Document   : tableID
    Created on : 18 thg 10, 2024, 21:38:56
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page import="model.Table" %>
<%@ page import="dal.TableDAO" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            @import url('https://fonts.googleapis.com/css?family=Montserrat:400,800');

            .bg-image {
                background-image: url('https://raimuhome.vn/storage/photos/8/thiet-ke-nha-hang-phong-cach-nhat-ban-4.jpg');
                background-size: cover;
                background-position: center;
                height: 100vh;
                position: relative;
            }

            .overlay {
                background-color: rgba(0, 0, 0, 0.6);
                position: absolute;
                top: 0;
                bottom: 0;
                left: 0;
                right: 0;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .content {
                color: white;
                text-align: center;
            }

            .content h1 {
                font-size: 2.5rem;
                margin-bottom: 30px;
            }

            .buttons .btn {
                width: 150px;
                height: 50px;
                font-size: 1.2rem;
                margin: 10px;
            }

            footer {
                background-color: #222;
                color: #fff;
                font-size: 14px;
                bottom: 0;
                position: fixed;
                left: 0;
                right: 0;
                text-align: center;
                z-index: 999;
            }

            .form-input {
                width: 300px;
                padding: 10px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 5px;
                margin-bottom: 20px;
            }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
           <div class="container-fluid bg-image">
            <div class="overlay">
                <div class="content">
                    <form action="dish" method="get" class="form-custom">
                        <h1>Enter Table ID</h1>
                        <input type="text" id="inputField" name="tableID" class="form-input" required />
                        <div class="buttons">
                            <button type="submit" class="btn btn-danger">Submit</button>
                        </div>
                        <c:set var="e" value="${requestScope.error}">
                        </c:set>
                        <p style="color: red">${e}</p>
                    </form>
                </div>
            </div>
        </div>
     
        <%@include file="footer.jsp" %>
    
    </body>
</html>
