/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import model.OrderDetail;

/**
 *
 * @author TQT
 */
@WebServlet(name = "UrgeItem", urlPatterns = {"/urge"})
public class UrgeItem extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UrgeItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UrgeItem at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer tableId = (Integer) session.getAttribute("tableID");
        String orderDetailId = request.getParameter("orderDetailId");
        session.removeAttribute("message");
        session.removeAttribute("error");
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        try {
            if (orderDetailId == null || orderDetailId.trim().isEmpty()) {
                throw new ServletException("Order detail ID is required");
            }
            Map<String, Long> lastUrgeMap = (Map<String, Long>) session.getAttribute("lastUrgeMap");
            if (lastUrgeMap == null) {
                lastUrgeMap = new HashMap<>();
            }
            Long lastUrgeTime = lastUrgeMap.get(orderDetailId);
            long currentTime = System.currentTimeMillis();

            if (lastUrgeTime != null && (currentTime - lastUrgeTime) < 300000) {
                session.setAttribute("message", "Please wait 5 minutes before urging this item again!");
                response.sendRedirect("viewOrder?tableID=" + tableId);
                return;
            }
            OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
            OrderDetail orderDetail = orderDetailDAO.getOrderDetailById(Integer.parseInt(orderDetailId));

            if (orderDetail == null) {
                throw new ServletException("Order detail not found");
            }

            if ("Served".equals(orderDetail.getStatus())) {
                session.setAttribute("message", "This item has already been served!");
                response.sendRedirect("viewOrder?tableID=" + tableId);
                return;
            }

            orderDetail.setUrgent(true);
            orderDetailDAO.updateOrderDetail(orderDetail);

            lastUrgeMap.put(orderDetailId, currentTime);
            session.setAttribute("lastUrgeMap", lastUrgeMap);
            session.setAttribute("message", "Order has been urged successfully!");

        } catch (Exception e) {
            session.setAttribute("message", "An error occurred: " + e.getMessage());
        }

        response.sendRedirect("viewOrder?tableID=" + tableId);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
