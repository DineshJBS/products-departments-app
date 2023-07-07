package com.letsshop.dto;

public class ProductDTO {
    private int productId;
    private String productName;

    private String deptName;

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductDTO(int productId, String productName, String deptName, int price) {
        this.productId = productId;
        this.productName = productName;
        this.deptName = deptName;
        this.price = price;
    }

    public ProductDTO() {
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
