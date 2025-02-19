<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.5.0/font/bootstrap-icons.css" rel="stylesheet">
        <link rel="stylesheet" href="css/menu.css"/>
        <style>
            .alert {
                padding: 15px;
                margin-bottom: 20px;
                border: 1px solid transparent;
                border-radius: 4px;
            }
            .alert-success {
                color: #155724;
                background-color: #d4edda;
                border-color: #c3e6cb;
            }
            .alert-danger {
                color: #721c24;
                background-color: #f8d7da;
                border-color: #f5c6cb;
            }
            .table-responsive {
                margin-top: 20px;
            }
            .dish-table {
                width: 100%;
                margin-bottom: 1rem;
                background-color: transparent;
            }
            .dish-table th, .dish-table td {
                padding: 1rem;
                vertical-align: middle;
            }
            .order-summary {
                margin: 20px 0;
                padding: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                background-color: #f8f9fa;
            }
            .order-items {
                margin-top: 10px;
            }
            .quantity-badge {
                background-color: #ffc107;
                color: #000;
                padding: 0.25em 0.6em;
                border-radius: 50%;
                margin-left: 5px;
            }

            .card {
                border: 1px solid #ddd;
                border-radius: 8px;
                transition: transform 0.3s;
            }

            .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            }

            .card-img-top {
                border-bottom: 1px solid #ddd;
                border-radius: 8px 8px 0 0;
            }

            .card-title {
                font-size: 1.1rem;
                font-weight: bold;
            }

            .card-text {
                margin: 0.5rem 0;
                color: #333;
            }

            .card-footer {
                background-color: #f8f9fa;
                border-top: 1px solid #ddd;
            }

            .badge {
                font-size: 0.9rem;
                margin-top: 5px;
                display: inline-block;
            }

        </style>
    </head>
    <body>
        <!--header-->
        <%@include file="header.jsp" %>
<div class="container-fluid py-4">
            <!-- Table ID Display and Current Order -->
            <div class="row">
                <div class="col-md-6">
                    <h2 class="mb-4">You are ordering for table: ${sessionScope.tableID}</h2>
                    <c:if test="${not empty sessionScope.successMessage}">
                        <div class="alert alert-success mt-3">
                            ${sessionScope.successMessage}
                            <% session.removeAttribute("successMessage"); %>
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.errorMessage}">
                        <div class="alert alert-danger mt-3">
                            ${sessionScope.errorMessage}
                            <% session.removeAttribute("errorMessage"); %>
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-info">
                            ${sessionScope.message}
                            <% session.removeAttribute("message"); %>
                        </div>
                    </c:if>
                    <c:if test="${not empty sessionScope.error}">
                        <div class="alert alert-info">
                            ${sessionScope.error}
                            <% session.removeAttribute("error"); %>
                        </div>
                    </c:if>
                </div>
                <div class="col-md-6">
                    <div class="order-summary">
                        <h4>Current Order for Table ${sessionScope.tableID}</h4>
                        <div class="order-items">
                            <div class="order-items">
                                <c:if test="${not empty sessionScope.orderList}">
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Dish Name</th>
                                                <th>Quantity</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="dish" items="${sessionScope.orderList}">
                                                <tr>
                                                    <td>${dish.dishName}</td>
                                                    <td>
                                                        <span class="quantity-badge">${dish.quantity}</span>
                                                    </td>
                                                    <td>
<div class="btn-group" role="group">
                                                            <!-- Separate form for delete -->
                                                            <form action="deleteItem" method="get" class="me-2 d-inline">
                                                                <input type="hidden" name="dishId" value="${dish.dishId}">
                                                                <button type="submit" class="btn btn-danger btn-sm">
                                                                    <i class="bi bi-trash"></i> Delete
                                                                </button>
                                                            </form>
                                                            <!-- Separate form for update -->
                                                            <form action="updateItem" method="get" class="d-flex align-items-center d-inline">
                                                                <input type="hidden" name="dishId" value="${dish.dishId}">

                                                                <div class="input-group input-group-sm me-2" style="width: 120px;">
                                                                    <button type="button" class="btn btn-outline-secondary" onclick="this.nextElementSibling.stepDown()">
                                                                        −
                                                                    </button>
                                                                    <input type="number" min="1" step="1" name="quantity" value="${dish.quantity}" 
                                                                           class="form-control text-center" style="width: 50px;">
                                                                    <button type="button" class="btn btn-outline-secondary" onclick="this.previousElementSibling.stepUp()">
                                                                        +
                                                                    </button>
                                                                </div>

                                                                <button type="submit" class="btn btn-primary btn-sm">
                                                                    <i class="bi bi-arrow-clockwise"></i> Update
                                                                </button>
                                                            </form>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
<c:if test="${empty sessionScope.orderList}">
                                    <p class="text-muted">No items in order yet!</p>
                                </c:if>
                            </div>


                            <!-- Separate form for final order -->
                            <form action="orderFinal" method="post">
                                <button type="submit" class="btn btn-success" 
                                        ${empty sessionScope.orderList ? 'disabled' : ''}>
                                    <i class="bi bi-check-circle"></i>Order
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Menu Items Display -->
                <div class="container-fluid">
                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
                        <c:forEach var="dish" items="${data}">
                            <div class="col">
                                <div class="card h-100">
                                    <img src="${dish.image}" alt="Dish Image" class="card-img-top img-thumbnail" style="max-height: 200px; object-fit: cover;">
                                    <div class="card-body">
                                        <h5 class="card-title">${dish.dishName}</h5>
                                        <p class="card-text">$${dish.price}</p>
                                        <span class="badge ${dish.status == 'yes' ? 'bg-success' : 'bg-danger'}">
                                            ${dish.status}
                                        </span>
                                    </div>
                                    <div class="card-footer text-center">
                                        <form action="add" method="post">
                                            <input type="hidden" name="tableID" value="${sessionScope.tableID}">
                                            <input type="hidden" name="dishId" value="${dish.dishId}">
                                            <input type="hidden" name="name" value="${dish.dishName}">
                                            <input type="hidden" name="price" value="${dish.price}">
                                            <input type="hidden" name="status" value="${dish.status}">
                                            <input type="hidden" name="image" value="${dish.image}">
                                            <button type="submit" class="btn btn-warning" 
                                                    ${dish.status != 'yes' ? 'disabled' : ''}>
                                                <i class="bi bi-plus-circle"></i> Add to Order
                                            </button>
                                        </form>
                                    </div>
                                </div>
</div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>

        <!--footer-->
        <%@include file="footer.jsp" %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/order.js"></script>
    </body>
</html>