<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <meta http-equiv="refresh" content="10">
        <style>
            html, body {
                height: 100%;
                margin: 0;
            }
            .container {
                min-height: calc(100vh - 100px);
            }
            .menu-item img {
                width: 100%;
                height: 300px;
                object-fit: cover;
                border-radius: 8px;
            }
            .menu-item {
                margin-bottom: 20px;
            }
            .menu-item h5 {
                margin-top: 10px;
                font-size: 1.25rem;
                font-weight: 600;
            }
            .menu-item .price {
                font-size: 1.1rem;
                color: #5D4037;
                margin-bottom: 10px;
            }
            .urgent-row {
                background-color: #fff3cd;
                animation: urgent-flash 2s infinite;
            }
            @keyframes urgent-flash {
                0% {
                    background-color: #fff3cd;
                }
                50% {
                    background-color: #ffe5d0;
                }
                100% {
                    background-color: #fff3cd;
                }
            }
        </style>
        <title>Order Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <div class="container mt-4">
            <h2>Order processing for table ${sessionScope.tableID}</h2>

            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-info">
                    ${sessionScope.message}
                    <% session.removeAttribute("message"); %>
                </div>
            </c:if>

            <table class="table">
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty orderItems}">
                        <!-- First display urgent items -->
                        <c:forEach var="item" items="${orderItems}">
                            <c:if test="${item.urgent && item.status != 'Done'}">
                                <tr class="urgent-row">
                                    <td>
                                        <span class="badge bg-danger me-2">Urgent!</span>
                                        ${item.dishName}
                                    </td>
                                    <td>${item.quantity}</td>
                                    <td>$${item.price}</td>
                                    <td>
                                        <span class="badge ${item.status eq 'Served' ? 'bg-success' : 
                                                             item.status eq 'Waiting' ? 'bg-warning' : 'bg-danger'}">
                                                  ${item.status}
                                              </span>
                                        </td>
                                        <td>
                                            <c:if test="${item.status eq 'Waiting'}">
                                                <form action="urge" method="get" class="me-2">
                                                    <input type="hidden" name="orderDetailId" value="${item.orderDetailId}">
                                                    <button type="button" class="btn btn-secondary btn-sm" disabled>
                                                        Urged!
                                                    </button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <!-- Then display non-urgent items -->


                            <c:forEach var="item" items="${orderItems}">
                                <c:if test="${!item.urgent && item.status != 'Done'}">
                                    <tr>
                                        <td>${item.dishName}</td>
                                        <td>${item.quantity}</td>
                                        <td>$${item.price}</td>
                                        <td>
                                            <span class="badge ${item.status eq 'Served' ? 'bg-success' : 
                                                                 item.status eq 'Waiting' ? 'bg-warning' : 'bg-danger'}">
                                                      ${item.status}
                                                  </span>
                                            </td>
                                            <td>
                                                <audio id="urgeSound" src="sound/Messenger notification sound.mp3"></audio>

                                                <c:if test="${item.status eq 'Waiting'}">
                                                    <form action="urge" method="get" class="me-2" onsubmit="playUrgeSound(event)">
                                                        <input type="hidden" name="orderDetailId" value="${item.orderDetailId}">
                                                        <c:choose>
                                                            <c:when test="${empty sessionScope.lastUrgeMap[item.orderDetailId] || 
                                                                            ((pageContext.session.lastAccessedTime - sessionScope.lastUrgeMap[item.orderDetailId]) > 300000)}">
                                                                    <!-- Use the onclick event to play the sound -->
                                                                    <button type="submit" class="btn btn-warning btn-sm" onclick="playUrgeSound(event)">
                                                                        Urge!
                                                                    </button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button type="button" class="btn btn-secondary btn-sm" disabled>
                                                                    Urged!
                                                                </button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty orderItems}">
                                <tr>
                                    <td colspan="5" class="text-center">No orders found for this table</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <%@include file="footer.jsp" %>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                                                                        function playUrgeSound(event) {
                                                                            // Prevent the form from submitting immediately
                                                                            event.preventDefault();

                                                                            // Get the audio element and play the sound
                                                                            var urgeSound = document.getElementById("urgeSound");

                                                                            if (urgeSound) {
                                                                                urgeSound.volume = 1.0; // Set volume to maximum
                                                                                urgeSound.play().then(() => {
                                                                                    console.log("Sound played successfully!");
                                                                                }).catch((error) => {
                                                                                    console.error("Error playing sound:", error);
                                                                                });
                                                                            } else {
                                                                                console.error("Audio element not found!");
                                                                            }

                                                                            // Submit the form after the sound is played
                                                                            setTimeout(function () {
                                                                                event.target.submit();
                                                                            }, 500); // Adjust the delay (in milliseconds) as needed
                                                                        }
                </script>
            </body>

        </html>