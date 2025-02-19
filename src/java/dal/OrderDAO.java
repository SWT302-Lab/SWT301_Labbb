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
import model.Order;
import model.OrderDetail;
import model.SubOrder;

/**
 *
 * @author ADMIN
 */
public class OrderDAO extends DBContext {

    public void setOrderStatus(int tableId) {
        String sql = """
                     UPDATE [dbo].[Order]
                        SET [OrderStatus] = 'Done'
                      WHERE [TableID] = ?
                     """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, tableId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void insertOrder(Order o) {
        try {
            String sql = "insert into [Order](TableID) values (?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, o.getTableId().getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
public List<SubOrder> getAllItemsByTableId(int tableId) {
    List<SubOrder> listOrders = new ArrayList<>();
    String sql = """
                 select o.TableID, o.OrderID, od.OrderDetailID, d.DishID, d.DishName, od.Quantity, od.Price, od.Status 
                 from [Order] o
                 join OrderDetail od on o.OrderID = od.OrderID
                 join Dish d on d.DishID = od.DishID 
                 where o.TableID = ? and o.OrderStatus != 'Done'
                 """;
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, tableId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            SubOrder so = new SubOrder();
            so.setTableId(rs.getInt("TableID"));
            so.setOrderID(rs.getInt("OrderID"));
            so.setOrderDetailId(rs.getInt("OrderDetailID"));
            so.setDishId(rs.getInt("DishID"));
            so.setDishName(rs.getString("DishName"));
            so.setQuantity(rs.getInt("Quantity"));
            so.setPrice(rs.getInt("Price"));
            so.setStatus(rs.getString("Status"));
            listOrders.add(so);
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return listOrders;
}

    public int getLatestOrderId() {
        int orderId = 0;
        try {
            String sql = "SELECT TOP 1 OrderID FROM [Order] ORDER BY OrderID DESC";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt("OrderID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orderId;
    }
    
    public void insertOrderDetail(OrderDetail od) {
        try {
            String sql = "insert into OrderDetail(OrderID, DishID, Quantity, Price) values(?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, od.getOrderId());
            st.setInt(2, od.getDishId());
            st.setInt(3, od.getQuantity());
            st.setInt(4, od.getPrice());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
