/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 *
 * @author ADMIN
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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

        String formName = request.getParameter("formName");
        if ("form1".equals(formName)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.isEmpty() || username == null || password == null || password.isEmpty()) {
                String errorMessage = "Username or password are incorrect";
                response.sendRedirect("index.html?error="
                        + URLEncoder.encode(errorMessage, "UTF-8") + "&form=form1");
            }

            AccountDAO aO = new AccountDAO();
            if ("waiter".equals(aO.getAccount(username, password))) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("tableID.jsp");
            } else if ("cashier".equals(aO.getAccount(username, password))) {
                response.sendRedirect("cashier");
            } else {
                String errorMessage = "Username or password are incorrect";
                response.sendRedirect("index.html?error="
                        + URLEncoder.encode(errorMessage, "UTF-8") + "&form=form1");
            }
        } else if ("form2".equals(formName)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username.isEmpty() || username == null || password == null || password.isEmpty()) {
                String errorMessage = "Username or password are incorrect";
                response.sendRedirect("index.html?error="
                        + URLEncoder.encode(errorMessage, "UTF-8") + "&form=form2");
                return;
            }

            AccountDAO aO = new AccountDAO();
            if ("admin".equals(aO.getAccount(username, password))) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("admin.jsp");
            } else {
                String errorMessage = "Username or password are incorrect";
                response.sendRedirect("index.html?error="
                        + URLEncoder.encode(errorMessage, "UTF-8") + "&form=form2");
            }
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
