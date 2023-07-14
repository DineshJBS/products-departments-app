package com.letsshop.dto;

public class Orders {
    private int orderId;
    private String productName;
    private int price;
    private String orderDate;

    public Orders(int orderId, String productName, int price, String orderDate) {
        this.orderId = orderId;
        this.productName = productName;
        this.price = price;
        this.orderDate = orderDate;
    }

    public Orders() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
        return "Orders{" +
                "orderId=" + orderId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}
