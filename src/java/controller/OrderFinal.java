/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import jakarta.servlet.http.HttpSession;
import model.Dish;
import model.Order;
import model.OrderDetail;
import model.SubOrder;
import model.Table;

/**
 *
 * @author TQT
 */
@WebServlet(name = "OrderFinal", urlPatterns = {"/orderFinal"})
public class OrderFinal extends HttpServlet {

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
            out.println("<title>Servlet OrderFinal</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderFinal at " + request.getContextPath() + "</h1>");
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
//        
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
        HttpSession session = request.getSession();
        Integer tableId = (Integer) session.getAttribute("tableID");
        List<Dish> orderList = (List<Dish>) session.getAttribute("orderList");
        
        // Clear any previous messages first
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");

        // Validate data
        if (tableId == null || orderList == null || orderList.isEmpty()) {
            session.setAttribute("errorMessage", "Cannot submit empty order");
            response.sendRedirect("dish?tableID=" + tableId);
            return;
        }

        try {
            // Create new Order
            Order order = new Order();
            Table table = new Table();
            table.setId(tableId);
            order.setTableId(table);
            
            // Insert Order
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.insertOrder(order);
            
            // Get the latest OrderID
            int orderId = orderDAO.getLatestOrderId();
            
            // Insert OrderDetails for each item
            for (Dish item : orderList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setDishId(item.getDishId());
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setPrice((int) item.getPrice());
                orderDAO.insertOrderDetail(orderDetail);
            }
            
            // Clear the order list from session
            session.removeAttribute("orderList");
            
            // Set success message in session
            session.setAttribute("successMessage", "Order successfully");
            
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Error processing order: " + e.getMessage());
        }
        
        // Use sendRedirect instead of forward
        response.sendRedirect("dish?tableID=" + tableId);
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
