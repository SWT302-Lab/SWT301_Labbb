
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
import model.Dish;

/**
 *
 * @author Acer
 */
@WebServlet(name = "updateDish", urlPatterns = {"/updateDish"})
public class updateDish extends HttpServlet {

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
            out.println("<title>Servlet updateDish</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateDish at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");

        try {
            int id = Integer.parseInt(id_raw); // Chuyển đổi id từ chuỗi sang số nguyên
            DishDAO dao = new DishDAO();
            Dish d = dao.findId(id); // Tìm món ăn theo id

            if (d != null) {
                // Nếu tìm thấy món ăn, gửi thông tin đến trang update.jsp
                request.setAttribute("dish", d); // Đặt đối tượng Dish vào attribute
                request.getRequestDispatcher("updateDish.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy món ăn, thông báo lỗi
                request.setAttribute("error", "Dish with ID " + id + " not found.");
                request.getRequestDispatcher("updateDish.jsp").forward(request, response); // Quay lại danh sách
            }
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu id không phải là số nguyên
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("listDish.jsp").forward(request, response); // Quay lại danh sách
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.out.println(e);
            request.setAttribute("error", "An unexpected error occurred.");
            request.getRequestDispatcher("listDish.jsp").forward(request, response); // Quay lại danh sách
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
        //processRequest(request, response);
    String id_raw = request.getParameter("id");
    String name = request.getParameter("name");
    String price_raw = request.getParameter("price");
    String status = request.getParameter("status");
    String image = request.getParameter("image");

    try {
        int id = Integer.parseInt(id_raw);
        double price = Double.parseDouble(price_raw); // Chuyển đổi giá từ chuỗi sang số thực
        DishDAO dao = new DishDAO();
        // Tạo đối tượng Dish với dữ liệu mới
        Dish newDish = new Dish(id, name, price, status, image);
        dao.update(newDish);
        // Sau khi cập nhật thành công, chuyển hướng về trang danh sách
        response.sendRedirect("getDish");

    }  catch (NumberFormatException e) {
        // Xử lý lỗi nếu id hoặc price không phải là số hợp lệ
        request.setAttribute("id", id_raw); // Thêm dòng này
        request.setAttribute("error", "Invalid input for ID or price. Please enter numeric values.");
        request.getRequestDispatcher("updateDish.jsp").forward(request, response);
    } catch (Exception e) {
        // Xử lý các lỗi khác
        request.setAttribute("id", id_raw); // Thêm dòng này
        request.setAttribute("error", "An unexpected error occurred while updating the dish.");
        request.getRequestDispatcher("updateDish.jsp").forward(request, response);
    }
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
