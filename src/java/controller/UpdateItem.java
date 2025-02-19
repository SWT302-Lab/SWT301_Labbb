/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import model.Dish;

/**
 *
 * @author TQT
 */
@WebServlet(name = "UpdateItem", urlPatterns = {"/updateItem"})
public class UpdateItem extends HttpServlet {

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
            out.println("<title>Servlet UpdateItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateItem at " + request.getContextPath() + "</h1>");
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
        String quantity_raw = request.getParameter("quantity");
        String dishId = request.getParameter("dishId");

        try {
            Integer tableId = (Integer) session.getAttribute("tableID");
            if (tableId == null) {
                throw new ServletException("No table ID found in session");
            }

            int quantity = Integer.parseInt(quantity_raw);
            int dishIdInt = Integer.parseInt(dishId);

            if (quantity <= 0) {
                session.setAttribute("error", "Please input valid quantity");
            } else {
                List<Dish> orderList = (List<Dish>) session.getAttribute("orderList");

                if (orderList != null) {
                    boolean found = false;
                    for (Dish dish : orderList) {
                        if (dish.getDishId() == dishIdInt) {
                            dish.setQuantity(quantity);
                            found = true;
                            session.setAttribute("message", "Quantity updated successfully");
                            break;
                        }
                    }
                    if (!found) {
                        session.setAttribute("error", "Dish not found in order");
                    }
                } else {
                    session.setAttribute("error", "No order found");
                }
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Please input valid quantity");
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        response.sendRedirect("dish?tableID=" + session.getAttribute("tableID"));
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
