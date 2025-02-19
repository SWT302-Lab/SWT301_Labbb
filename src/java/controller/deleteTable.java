/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.TableDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Table;

/**
 *
 * @author Acer
 */
@WebServlet(name = "deleteTable", urlPatterns = {"/deleteTable"})
public class deleteTable extends HttpServlet {

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
            out.println("<title>Servlet deleteTable</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteTable at " + request.getContextPath() + "</h1>");
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
        String id_raw = request.getParameter("id");
        try {
            int id = Integer.parseInt(id_raw); // Kiểm tra id có phải số nguyên
            TableDAO dao = new TableDAO();
            Table table = dao.findTableById(id); // Tìm bàn theo id trước khi xóa

            if (table != null) { // Nếu tìm thấy bàn với id hợp lệ
                if(table.getStatus().equals("occupied")){
                request.setAttribute("error", "Table is occupied ");
                request.getRequestDispatcher("getTable").forward(request, response); // Trả về danh sách bàn với thông báo lỗi    
                return;
                }
                dao.deleteTable(id); // Xóa bàn theo ID
                response.sendRedirect("getTable"); // Redirect về trang danh sách sau khi xóa thành công
            } else {
                // Nếu không tìm thấy bàn với ID cung cấp
                request.setAttribute("error", "Table with ID " + id + " not found.");
                request.getRequestDispatcher("getTable").forward(request, response); // Trả về danh sách bàn với thông báo lỗi
            }
        } catch (NumberFormatException e) {
            // Nếu ID nhập không phải là số nguyên
            request.setAttribute("error", "Invalid ID format. Please enter a numeric value.");
            request.getRequestDispatcher("getTable").forward(request, response); // Trả về trang danh sách bàn với thông báo lỗi
        } catch (Exception e) {
            // Bắt lỗi không mong đợi khác
            System.out.println("Error while deleting table: " + e.getMessage());
            request.setAttribute("error", "An unexpected error occurred while deleting the table. Please try again.");
            request.getRequestDispatcher("listTable.jsp").forward(request, response); // Trả về trang danh sách bàn với thông báo lỗi
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
