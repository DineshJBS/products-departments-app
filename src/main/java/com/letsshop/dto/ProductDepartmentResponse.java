package com.letsshop.dto;

public class ProductDepartmentResponse {
    private String productName;
    private String deptName;

    public ProductDepartmentResponse() {
    }

    public ProductDepartmentResponse(String productName, String deptName) {
        this.productName = productName;
        this.deptName = deptName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
