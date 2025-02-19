
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
@WebServlet(name="updateTable", urlPatterns={"/updateTable"})
public class updateTable extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet updateTable</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateTable at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
            int id = Integer.parseInt(id_raw); // Chuyển đổi id từ chuỗi sang số nguyên
            TableDAO dao = new TableDAO();
            Table t = dao.findTableById(id); // Tìm bàn theo id

            if (t != null) {
                // Nếu tìm thấy bàn, gửi thông tin đến trang updateTable.jsp
                request.setAttribute("table", t);
                request.getRequestDispatcher("updateTable.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy bàn
                request.setAttribute("error", "Table with ID " + id + " not found.");
                request.getRequestDispatcher("updateTable.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu id không phải là số nguyên
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("listTable.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
            request.setAttribute("error", "An unexpected error occurred.");
            request.getRequestDispatcher("listTable.jsp").forward(request, response);
        }
    

    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id_raw = request.getParameter("id");
        String status = request.getParameter("status");

        try {
            int id = Integer.parseInt(id_raw); // Chuyển đổi id từ chuỗi sang số nguyên
            TableDAO dao = new TableDAO();
            // Tạo đối tượng Table với dữ liệu mới
            Table newTable = new Table(id, status);
            dao.updateTable(newTable);
            // Sau khi cập nhật thành công, chuyển hướng về trang danh sách
            response.sendRedirect("getTable");

        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu id không phải là số hợp lệ
            request.setAttribute("id", id_raw);
            request.setAttribute("error", "Invalid input for ID. Please enter a numeric value.");
            request.getRequestDispatcher("updateTable.jsp").forward(request, response);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            request.setAttribute("id", id_raw);
            request.setAttribute("error", "An unexpected error occurred while updating the table.");
            request.getRequestDispatcher("updateTable.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
