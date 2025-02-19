/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Staff;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */
public class StaffDAO extends DBContext{
     public List<Staff> getAll() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM Staff";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = getAccountByAccountId(rs.getInt(2));
                Staff s = new Staff(rs.getInt(1), rs.getString(3), rs.getString(4), account);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // Tìm nhân viên theo ID
    public Staff findId(int id) {
        String sql = "SELECT * FROM Staff WHERE StaffID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Account account = getAccountByAccountId(rs.getInt(2));
                return new Staff(rs.getInt(1), rs.getString(3), rs.getString(4), account);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // Thêm nhân viên mới
    public void insert(Staff s) {
        String sql = "INSERT INTO Staff (AccountID, Name, Role) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, s.getAccount().getAccountID());
            st.setString(2, s.getStaffName());
            st.setString(3, s.getStaffRole());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Cập nhật thông tin nhân viên
    public void update(Staff s) {
        String sql = "UPDATE Staff SET Name = ?, Role = ? WHERE StaffID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, s.getStaffName());
            st.setString(2, s.getStaffRole());
            st.setInt(3, s.getStaffId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Xóa nhân viên theo ID
    public void delete(int id) {
        String sql = "DELETE FROM Staff WHERE StaffID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Lấy tài khoản theo AccountID
    private Account getAccountByAccountId(int accountId) {
        String sql = "SELECT * FROM Account WHERE AccountID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, accountId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
      public void insertStaffWithAccount(Staff staff) {
        PreparedStatement psAccount = null;
        PreparedStatement psStaff = null;
        ResultSet rs = null;
        
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // 1. Thêm Account
            String sqlAccount = "INSERT INTO Account (Username, Password, Role) VALUES (?, ?, ?)";
            psAccount = connection.prepareStatement(sqlAccount, PreparedStatement.RETURN_GENERATED_KEYS);
            psAccount.setString(1, staff.getAccount().getUsername());
            psAccount.setString(2, staff.getAccount().getPassword());
            psAccount.setString(3, staff.getAccount().getRole());
            psAccount.executeUpdate();

            // Lấy AccountID vừa được tạo
            rs = psAccount.getGeneratedKeys();
            int accountId = 0;
            if (rs.next()) {
                accountId = rs.getInt(1);
            }

            // 2. Thêm Staff
            String sqlStaff = "INSERT INTO Staff (StaffID, AccountID, Name, Role) VALUES (?, ?, ?, ?)";
            psStaff = connection.prepareStatement(sqlStaff);
            psStaff.setInt(1, staff.getStaffId());
            psStaff.setInt(2, accountId); // AccountID mới từ bảng Account
            psStaff.setString(3, staff.getStaffName());
            psStaff.setString(4, staff.getStaffRole());
            psStaff.executeUpdate();

            // Nếu không có lỗi, commit thay đổi
            connection.commit();
        } catch (SQLException e) {
            try {
                // Rollback nếu có lỗi
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Khôi phục trạng thái auto-commit
                connection.setAutoCommit(true);

                // Đóng tài nguyên
                if (rs != null) rs.close();
                if (psAccount != null) psAccount.close();
                if (psStaff != null) psStaff.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

