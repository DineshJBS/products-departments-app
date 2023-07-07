package com.letsshop.entity;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "user_product")
public class UserProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "user_id")
    private int userId;

    @Column(name="product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "order_date")
    private String orderDate;

    public UserProduct( int userId, String productName, int price, String orderDate) {

        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.orderDate = orderDate;
    }

    public UserProduct() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
