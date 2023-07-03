package com.letsshop.repository;

import com.letsshop.dto.ProductDepartmentResponse;
import com.letsshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByProductName(String productName);

    //    @Query(value="SELECT product_name, dept_name from product \n" +
//       "join department on product.dept_id = department.dept_id", nativeQuery = true)

    @Query("SELECT new com.letsshop.dto.ProductDepartmentResponse(p.productName, d.deptName)" +
            " FROM Product p JOIN Department d ON p.department = d.deptId WHERE p.deleteOrNo = 0")
    List<ProductDepartmentResponse> getProductsAndDepartments();
}
