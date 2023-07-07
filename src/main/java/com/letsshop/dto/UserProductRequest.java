package com.letsshop.dto;

public class UserProductRequest {

    private int orderId;
    private int userId;
    private String productName;
    private int price;
    private String orderDate;

    public UserProductRequest(int orderId, int userId, String productName, int price, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productName = productName;
        this.price = price;
        this.orderDate = orderDate;
    }

    public UserProductRequest(String productName, int price) {
        this.productName = productName;
        this.price = price;

    }

    public UserProductRequest() {
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
        return "UserProductRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
