/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DishDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

/**
 *
 * @author TQT
 */
@WebServlet(name = "AddItem", urlPatterns = {"/add"})
public class AddItem extends HttpServlet {

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
            out.println("<title>Servlet AddItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddItem at " + request.getContextPath() + "</h1>");
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

        try {
            // Get the table ID from the session
            Integer tableId = (Integer) session.getAttribute("tableID");
            if (tableId == null) {
                throw new ServletException("No table ID found in session");
            }

            // Get the order details from the form
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            String dishName = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            String status = request.getParameter("status");
            String image = request.getParameter("image");

            // Get or create the order list
            List<Dish> orderList = (List<Dish>) session.getAttribute("orderList");

            if (orderList == null) {
                orderList = new ArrayList<>();
                session.setAttribute("orderList", orderList);
            }

            // Check if the dish already exists in the order
            boolean dishExists = false;
            for (Dish existingDish : orderList) {
                if (existingDish.getDishId() == dishId) {
                    existingDish.incrementQuantity();
                    dishExists = true;
                    break;
                }
            }

            // If the dish doesn't exist, add it as a new item
            if (!dishExists) {
                Dish newDish = new Dish(dishId, dishName, price, status, image);
                newDish.setQuantity(1); // Set initial quantity to 1
                orderList.add(newDish);
            }

            request.setAttribute("message", "Item added to order successfully!");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input format");
        } catch (Exception e) {
            request.setAttribute("error", "Error processing order: " + e.getMessage());
        }
        response.sendRedirect("dish?tableID=" + session.getAttribute("tableID"));

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
