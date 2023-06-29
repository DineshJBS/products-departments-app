package com.letsshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private int deptId;


    @Column(name = "dept_name")
    private String deptName;


    @JsonManagedReference
    @OneToMany(mappedBy = "department",
        cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Product> products;

    public Department(String deptName) {
        this.deptName = deptName;
    }

    // convenience method for bidirectional relationship
    public void add(Product tempProduct){
        if(products == null){
            products = new ArrayList<>();
        }
        products.add(tempProduct);
        tempProduct.setDepartment(this);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Department(){}

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
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                // Exclude the products field from the string representation
                '}';
    }

}
