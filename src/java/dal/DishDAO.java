/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Dish;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Acer
 */

public class DishDAO extends DBContext {

    public ArrayList<Dish> getAll() {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "SELECT * FROM Dish";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Dish d = new Dish(
                    rs.getInt("DishID"),
                    rs.getString("DishName"),
                    rs.getDouble("Price"),
                    rs.getString("Status"),
                    rs.getString("ImageLink")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // Chèn món ăn mới
    public void insert(Dish d) {
        String sql = "INSERT INTO [dbo].[Dish] ( DishName, Price, Status,ImageLink) VALUES (?, ?, ?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
          
            st.setString(1, d.getDishName());
            st.setDouble(2, d.getPrice());
            st.setString(3, d.getStatus());
            st.setString(4, d.getImage());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Tìm món ăn theo ID
    public Dish findId(int id) {
        String sql = "SELECT * FROM Dish WHERE DishID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Dish(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    rs.getString(5)
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    // Xóa món ăn theo ID
    public void delete(int id) {
        String sql = "DELETE FROM [dbo].[Dish] WHERE DishID = ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, id);
        int affectedRows = st.executeUpdate();
        System.out.println("Deleted " + affectedRows + " row(s)"); // Thêm dòng này để kiểm tra số hàng bị xóa
    } catch (SQLException e) {
        System.out.println("Error while deleting dish: " + e.getMessage()); // Thêm dòng này để log lỗi
    }
    }

    // Cập nhật thông tin món ăn
    public void update(Dish d) {
        String sql = "UPDATE [dbo].[Dish] SET DishName = ?, Price = ?, Status = ?, ImageLink = ? WHERE DishID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, d.getDishName());
            st.setDouble(2, d.getPrice());
            st.setString(3, d.getStatus());
            st.setString(4, d.getImage());
            st.setInt(5, d.getDishId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public ArrayList<Dish> searchDishByName(String name) {
    ArrayList<Dish> list = new ArrayList<>();
    String sql = "SELECT * FROM Dish WHERE DishName LIKE ?";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, "%" + name + "%"); // Tìm kiếm với chuỗi chứa từ khóa
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Dish d = new Dish(
                rs.getInt("DishID"),
                rs.getString("DishName"),
                rs.getDouble("Price"),
                rs.getString("Status"),
                rs.getString("ImageLink")
            );
            list.add(d);
        }
    } catch (SQLException e) {
        System.out.println("Error while searching dishes: " + e.getMessage());
    }
    return list;
}
    
}
