package com.letsshop.dto;

import com.letsshop.entity.Product;

import java.util.List;

public class DepartmentDTO {

    private int deptId;

    private String deptName;

    private List<Product> products;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
