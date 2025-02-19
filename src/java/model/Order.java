/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class Order {

    private int orderId;
    private Table tableId;
    private LocalDateTime orderTime;

    public Order() {
    }

    public Order(int orderId, Table tableId, LocalDateTime orderTime) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.orderTime = orderTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Table getTableId() {
        return tableId;
    }

    public void setTableId(Table tableId) {
        this.tableId = tableId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

}
