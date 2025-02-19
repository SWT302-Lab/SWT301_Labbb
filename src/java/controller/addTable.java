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
import java.util.List;
import model.Table;

/**
 *
 * @author Acer
 */
@WebServlet(name = "addTable", urlPatterns = {"/addTable"})
public class addTable extends HttpServlet {

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
            out.println("<title>Servlet addTable</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addTable at " + request.getContextPath() + "</h1>");
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
        // Lấy thông tin bàn từ request và thêm vào database

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

        String id_raw = request.getParameter("id");
        

        // Bắt lỗi nếu nhập sai kiểu dữ liệu cho ID (ví dụ: nhập chữ thay vì số)
        int id;
        try {
            id = Integer.parseInt(id_raw);// Chuyển đổi chuỗi thành số nguyên
            if(id < 0){
            request.setAttribute("error", "Tableid must be > 0");
            request.getRequestDispatcher("./createTable.jsp").forward(request, response);
            return; // Kết thúc phương thức để không thực hiện tiếp các bước khác
    
            }
        } catch (NumberFormatException e) {
            // Nếu người dùng nhập ký tự không phải số, bắt lỗi và trả về thông báo
            request.setAttribute("error", "Invalid input for table ID. Please enter a numeric value.");
            request.getRequestDispatcher("./createTable.jsp").forward(request, response);
            return; // Kết thúc phương thức để không thực hiện tiếp các bước khác
        }

        TableDAO dao = new TableDAO();
        List<Table> tableList = dao.getAllTable();

        // Kiểm tra xem ID bàn có bị trùng không
        boolean isDuplicate = false;
        for (Table table : tableList) {
            if (table.getId() == id) {
                isDuplicate = true;
                break;
            }
        }

        // Nếu ID bàn bị trùng, thông báo lỗi và yêu cầu nhập lại
        if (isDuplicate) {
            request.setAttribute("error", "Table ID already exists. Please choose a different Table ID.");
            request.getRequestDispatcher("./createTable.jsp").forward(request, response);
            return;
        }

        try {
            Table newTable = new Table(id, "Available"); // Tạo đối tượng Table mới với ID và trạng thái
            dao.insertTable(newTable); // Gọi phương thức insertTable để thêm bàn mới vào database
            response.sendRedirect("getTable"); // Chuyển hướng người dùng tới trang hiển thị danh sách bàn sau khi thêm thành công
        } catch (Exception e) {
            // Bắt các lỗi không mong đợi khác
            System.out.println(e);
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("./createTable.jsp").forward(request, response); // Trả về trang tạo bàn với thông báo lỗi
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
