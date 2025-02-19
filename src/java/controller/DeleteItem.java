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
import java.util.Iterator;
import model.Dish;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TQT
 */
@WebServlet(name = "DeleteItem", urlPatterns = {"/deleteItem"})
public class DeleteItem extends HttpServlet {

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
            out.println("<title>Servlet DeleteItem</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteItem at " + request.getContextPath() + "</h1>");
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
        String dishId = request.getParameter("dishId");
        try {
            Integer tableId = (Integer) session.getAttribute("tableID");
            if (tableId == null) {
                throw new ServletException("No table ID found in session");
            }
            int dishIdInt = Integer.parseInt(dishId);
            List<Dish> orderList = (List<Dish>) session.getAttribute("orderList");
            if (orderList != null) {
                boolean removed = false;
                for (int i = 0; i < orderList.size(); i++) {
                    Dish dish = orderList.get(i);
                    if (dish.getDishId() == dishIdInt) {
                        orderList.remove(i);
                        removed = true;

                        // Check if this was the last item
                        if (orderList.isEmpty()) {
                            session.removeAttribute("orderList");
                        } else {
                            session.setAttribute("orderList", orderList);
                        }

                        session.setAttribute("message", "Item removed from order successfully");
                        break;
                    }
                }
                if (!removed) {
                    session.setAttribute("error", "Item not found in order");
                }
            } else {
                session.setAttribute("error", "No order found");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid dish ID");
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        // Make sure to redirect to dish servlet, not orderFinal
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
