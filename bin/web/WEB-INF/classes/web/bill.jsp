<%-- 
    Document   : bill
    Created on : 27 thg 10, 2024, 01:09:04
    Author     : ADMIN
--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Payment Summary</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>

        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Payment Summary</h2>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Dish Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total Amount</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderList}">
                        <c:if test="${order.status == 'Served'}">
                            <tr>
                                <td>${order.dishName}</td>
                                <td>${order.quantity}</td>
                                <td>${order.price}</td>
                                <td>${order.price * order.quantity}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
            <div class="text-right">
                <h4>Total Amount to Pay: ${totalAmount}</h4>
            </div>
            <div class="text-center mt-5">
                <h4>Scan to Pay</h4>
                <p>Use your banking app to scan the QR code below for payment.</p>
                <img src="images/qr.jpg" alt="Bank QR Code" class="img-fluid" style="max-width: 200px;">
            </div>
            <div class="text-center mt-4">
                <a href="cashier" class="btn btn-primary">Back to Cashier</a>
            </div>
        </div>
    </body>
</html>

