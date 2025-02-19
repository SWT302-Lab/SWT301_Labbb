/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Table;

/**
 *
 * @author ADMIN
 */
public class TableDAO extends DBContext {

    public String getTableStatusById(int id) {
        String status = "";
        String sql = "select TableStatus from [Table] where TableID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                status = rs.getString("TableStatus");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return status;
    }

    public List<Table> getAllTable() {
        List<Table> list = new ArrayList<>();
        String sql = "select * from [Table]";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Table tb = new Table(rs.getInt("TableID"),
                        rs.getString("TableStatus"));
                list.add(tb);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void insertTable(Table t) {
        String sql = "INSERT INTO [dbo].[Table] (TableID, TableStatus) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, t.getId());
            st.setString(2, t.getStatus());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Table findTableById(int id) {
        String sql = "SELECT * FROM [dbo].[Table] WHERE TableID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Table(
                        rs.getInt(1),
                        rs.getString(2)
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void deleteTable(int id) {
        String sql = "DELETE FROM [dbo].[Table] WHERE TableID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int affectedRows = st.executeUpdate();
            System.out.println("Deleted " + affectedRows + " row(s)");
        } catch (SQLException e) {
            System.out.println("Error while deleting table: " + e.getMessage());
        }
    }

    public void updateTable(Table t) {
        String sql = "UPDATE [dbo].[Table] SET TableStatus = ? WHERE TableID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, t.getStatus());
            st.setInt(2, t.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void setStatsus(int tableId, String status) {
        String sql = """
                     UPDATE [dbo].[Table]
                      SET   [TableStatus] = ?
                      WHERE [TableID] = ?
                     """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, tableId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public boolean checkIfTableOccupied(int tableID) {
    String sql = "SELECT TableStatus FROM [Table] WHERE TableID = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, tableID);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            String status = rs.getString("TableStatus");
            return "occupied".equals(status); // Trả về true nếu bàn bị chiếm, false nếu không
        }
    } catch (SQLException e) {
        System.out.println("Error checking table status: " + e.getMessage());
    }
    return false; // Trả về false nếu không tìm thấy bàn
}
}
