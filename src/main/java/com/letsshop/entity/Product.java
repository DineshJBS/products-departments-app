package com.letsshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

   @JoinColumn(name="dept_id")
   @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
   @JsonBackReference
   private Department department;

   private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int deleteOrNo;

    public int getDeleteOrNo() {
        return deleteOrNo;
    }

    public void setDeleteOrNo(int deleteOrNo) {
        this.deleteOrNo = deleteOrNo;
    }

    public Product(String productName, int price ){
        this.price = price;
        this.productName = productName;

    }


    public Product(){}

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", department=" + department +
                ", price=" + price +
                ", deleteOrNo=" + deleteOrNo +
                '}';
    }

}
