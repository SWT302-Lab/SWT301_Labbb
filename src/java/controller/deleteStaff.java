/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import dal.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Staff;

/**
 *
 * @author Acer
 */
@WebServlet(name = "deleteStaff", urlPatterns = {"/deleteStaff"})
public class deleteStaff extends HttpServlet {

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
            out.println("<title>Servlet deleteStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteStaff at " + request.getContextPath() + "</h1>");
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
            StaffDAO dao = new StaffDAO();
            Staff staff = dao.findId(id); // Tìm nhân viên theo id trước khi xóa
            if (staff != null) { // Nếu tìm thấy nhân viên với id hợp lệ
                AccountDAO accountDAO = new AccountDAO();
                Account account = accountDAO.findAccountByStaffId(id);
                if (account.getRole().equals("admin")) {
                    request.setAttribute("error", "Admin accounts cannot be deleted.");
                    request.getRequestDispatcher("getStaff").forward(request, response);
                    return;
                }
                // Xóa tài khoản trước khi xóa nhân viên
                accountDAO.delete(account.getAccountID());
                dao.delete(id); 
                response.sendRedirect("getStaff"); // Redirect về trang danh sách sau khi xóa thành công
            } else {
                request.setAttribute("error", "Staff with ID " + id + " not found.");
                request.getRequestDispatcher("getStaff").forward(request, response); // Trả về danh sách với thông báo lỗi
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format. Please enter a numeric value.");
            request.getRequestDispatcher("getStaff").forward(request, response); // Đổi về trang danh sách có thông báo lỗi
        } catch (Exception e) {
            System.out.println("Error while deleting staff: " + e.getMessage());
            request.setAttribute("error", "An unexpected error occurred while deleting the staff. Please try again.");
            request.getRequestDispatcher("getStaff").forward(request, response); // Đổi về trang danh sách có thông báo lỗi
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
