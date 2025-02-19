/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DishDAO;
import dal.TableDAO;
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
import model.Dish;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "DishServlet", urlPatterns = {"/dish"})
public class DishServlet extends HttpServlet {

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
            out.println("<title>Servlet DishServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DishServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int tableId = Integer.parseInt(request.getParameter("tableID"));
            session.setAttribute("tableID", tableId);
            TableDAO tdao = new TableDAO();
            if ("available".equals(tdao.getTableStatusById(tableId))) {
                DishDAO dAO = new DishDAO();
                List<Dish> list = dAO.getAll();

                // Get or create orderList for this table
                List<Dish> orderList = (List<Dish>) session.getAttribute("orderList");

                if (orderList == null) {
                    orderList = new ArrayList<>();
                    session.setAttribute("orderList", orderList);
                }

                request.setAttribute("data", list);
                request.getRequestDispatcher("menu.jsp").forward(request, response);
            } else {
                String error = "This table is currently unavailable";
                request.setAttribute("error", error);
                request.getRequestDispatcher("tableID.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            String error = "Invalid table ID format";
            request.setAttribute("error", error);
            request.getRequestDispatcher("tableID.jsp").forward(request, response);
        }
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
