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
import java.util.List;
import model.Account;
import model.Staff;

/**
 *
 * @author Acer
 */
@WebServlet(name = "addAccountandStaff", urlPatterns = {"/addAccountandStaff"})
public class addAccountandStaff extends HttpServlet {

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
            out.println("<title>Servlet addAccountandStaff</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addAccountandStaff at " + request.getContextPath() + "</h1>");
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String accountRole = request.getParameter("accountRole");
        String staffName = request.getParameter("staffName");
        String staffRole = request.getParameter("staffRole");

        try {
            AccountDAO accountDAO = new AccountDAO();
            StaffDAO staffDAO = new StaffDAO();

            // Lấy danh sách tất cả tài khoản từ cơ sở dữ liệu
            List<Account> accountList = accountDAO.getAll();

            // Kiểm tra username có bị trùng không
            boolean isDuplicate = false;
            for (Account account : accountList) {
                if (account.getUsername().equals(username)) {
                    isDuplicate = true;
                    break;
                }
            }

            // Nếu username bị trùng, thông báo lỗi
            if (isDuplicate) {
                request.setAttribute("error", "Username already exists. Please choose a different one.");
                request.getRequestDispatcher("addAccountandStaff.jsp").forward(request, response);
                return;
            }
            Account account = new Account(0, username, password, accountRole);
            Staff staff = new Staff(0, staffName, staffRole, account);

            staffDAO.insertStaffWithAccount(staff);  // Chèn cả Staff và Account
             
            response.sendRedirect("getStaff");  // Chuyển hướng đến trang danh sách nhân viên sau khi thành công
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while adding staff and account.");
            request.getRequestDispatcher("addAccountandStaff.jsp").forward(request, response);
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
