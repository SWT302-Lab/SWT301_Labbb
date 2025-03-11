<%-- 
    Document   : cashierProcess
    Created on : 22 thg 10, 2024, 11:05:42
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="./css/cashierManage.css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container mt-5">
            <div class="row">
                <div class="col-md-12 text-center">
                    <h2 class="text-uppercase">Order Summary</h2>
                    <p class="lead">Here is the list of dishes for table <strong>${param.tableID}</strong></p>
                </div>
            </div>

            <div class="row justify-content-center">
                <div class="col-md-8">
                    <div class="card shadow-sm">
                        <div class="card-header bg-dark text-white">
                            <h5 class="mb-0">Order Details</h5>
                        </div>
                        <div class="card-body p-0">
                            <table class="table table-hover table-striped">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>Dish Name</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>Total Amount</th>
                                        <th>Status</th>
                                        <th>Confirm</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="sum" value="0"></c:set>
                                    <c:forEach var="o" items="${requestScope.list}">
                                        <tr>
                                            <td>${o.dishName}</td>
                                            <td>${o.quantity}</td>
                                            <td>${o.price}</td>
                                            <td>${o.price * o.quantity}</td>
                                            <td>${o.status}</td>
                                            <c:if test="${o.status == 'Served'}">
                                                <c:set var="sum" value="${sum + (o.price * o.quantity)}" />
                                            </c:if>
                                            <td>
                                                <a class="btn btn-outline-primary"
                                                   onclick="submitForm('manage?dishId=${o.dishId}&orderId=${o.orderId}&tableId=${param.tableID}', 'post')">Confirm</a>
                                            </td>
                                            <td>
                                                <form id="myForm" method="post" style="display: none">
                                                    <input type="text" name="orderId" value="${o.orderId}">
                                                    <input type="text" name="dishId" value="${o.dishId}">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer text-right">
                            <h5>Total Amount: ${sum}</h5>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-md-12 text-center">
                    <a href="cashier" class="btn btn-primary btn-lg">Back to Tables</a>
                    <a href="tableManage?tableId=${param.tableID}&status=occupied" class="btn btn-danger btn-lg">Set Occupied</a>
                    <a href="tableManage?tableId=${param.tableID}&status=available" class="btn btn-success btn-lg">Set Vacant</a>
                    <a href="pay?tableId=${param.tableID}&status=available" class="btn btn-outline-primary btn-lg">Pay</a>
                </div>
            </div>
        </div>

        <script>
            function submitForm(action, method) {
                var form = document.getElementById('myForm');
                form.action = action;
                form.method = method;
                form.submit();
            }
        </script>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
