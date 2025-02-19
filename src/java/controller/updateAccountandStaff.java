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
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
@WebServlet(name = "updateAccountandStaff", urlPatterns = {"/updateAccountandStaff"})
public class updateAccountandStaff extends HttpServlet {

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
            out.println("<title>Servlet updateAccountandStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateAccountandStaff at " + request.getContextPath() + "</h1>");
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
            int id = Integer.parseInt(id_raw); // Chuyển đổi staffId từ chuỗi sang số nguyên
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.findId(id); // Tìm staff theo staffId

            if (staff != null) {
                // Gửi thông tin staff đến trang updateStaff.jsp
                request.setAttribute("staff", staff);
                request.getRequestDispatcher("updateAccountandStaff.jsp").forward(request, response);
            } else {
                // Nếu không tìm thấy staff, thông báo lỗi
                request.setAttribute("error", "Staff with ID " + id + " not found.");
                request.getRequestDispatcher("listStaff.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Staff ID.");
            request.getRequestDispatcher("listStaff.jsp").forward(request, response);
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
        String id_raw = request.getParameter("staffId");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String accountRole = request.getParameter("accountRole");
        String staffName = request.getParameter("staffName");
        String staffRole = request.getParameter("staffRole");
        AccountDAO accountdao = new AccountDAO();
        StaffDAO staffdao = new StaffDAO();
         try {
        // Lấy ID của Staff từ request
        int staffId = Integer.parseInt(id_raw);
        
        // Lấy Staff và Account hiện tại từ database
        Staff existingStaff = staffdao.findId(staffId);
        if (existingStaff != null) {
            // Lấy accountId từ Staff hiện tại
            int accountId = existingStaff.getAccount().getAccountID();
            String currentAccountRole = existingStaff.getAccount().getRole(); // Lấy vai trò hiện tại của tài khoản
            
            // Kiểm tra nếu vai trò hiện tại là "admin" và vai trò mới khác đi
            if (currentAccountRole.equals("admin") && !accountRole.equals("admin")) {
                // Nếu role là admin và bị thay đổi, không cho phép cập nhật
                request.setAttribute("error", "Cannot change role of an admin account.");
                request.getRequestDispatcher("updateAccountandStaff.jsp").forward(request, response);
                return; // Kết thúc phương thức nếu phát hiện không hợp lệ
            }

            // Tạo đối tượng Account với accountId đã tồn tại
            Account account = new Account(accountId, username, password, accountRole);
            // Tạo đối tượng Staff với staffId đã tồn tại
            Staff staff = new Staff(staffId, staffName, staffRole, account);

            // Cập nhật Account và Staff
            accountdao.update(account);
            staffdao.update(staff);

            // Chuyển hướng về danh sách staff sau khi cập nhật thành công
            response.sendRedirect("getStaff");
        } else {
            request.setAttribute("error", "Staff with ID " + staffId + " not found.");
            request.getRequestDispatcher("updateAccountandStaff.jsp").forward(request, response);
        }
    } catch (NumberFormatException e) {
        // Xử lý lỗi nếu ID không hợp lệ
        request.setAttribute("error", "Invalid Staff ID.");
        request.getRequestDispatcher("updateAccountandStaff.jsp").forward(request, response);
    } catch (Exception e) {
        // Xử lý các lỗi khác
        request.setAttribute("error", "An unexpected error occurred.");
        request.getRequestDispatcher("updateAccountandStaff.jsp").forward(request, response);
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
