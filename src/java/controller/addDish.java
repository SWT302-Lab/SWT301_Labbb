/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.DishDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Dish;

/**
 *
 * @author Acer
 */
@WebServlet(name = "addDish", urlPatterns = {"/addDish"})
public class addDish extends HttpServlet {

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
            out.println("<title>Servlet addDish</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addDish at " + request.getContextPath() + "</h1>");
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
        //processRequest(request, response);
   
        String name = request.getParameter("name");
        String price_raw = request.getParameter("price");
        String status = request.getParameter("status");
        String image = request.getParameter("image");
         DishDAO dao = new DishDAO();
         List<Dish> dishList = dao.getAll();

        // Kiểm tra xem tên món ăn có bị trùng không
        boolean isDuplicate = false;
        for (Dish dish : dishList) {
            if (dish.getDishName().equalsIgnoreCase(name)) {
                isDuplicate = true;
                break;
            }
        }

        // Nếu tên món ăn bị trùng, thông báo lỗi và yêu cầu nhập lại
        if (isDuplicate) {
            request.setAttribute("error", "Dish name already exists. Please choose a different name.");
            request.getRequestDispatcher("./createDish.jsp").forward(request, response);
            return;
        }
        try {
            double price = Double.parseDouble(price_raw);
            Dish newd = new Dish(0, name, price, status,image);
            dao.insert(newd);
            response.sendRedirect("getDish");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input for price. Please enter a numeric value.");
            request.getRequestDispatcher("./createDish.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("./createDish.jsp").forward(request, response);
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
