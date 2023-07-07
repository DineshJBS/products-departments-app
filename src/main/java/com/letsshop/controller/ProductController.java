package com.letsshop.controller;
import com.letsshop.dto.ProductDTO;
import com.letsshop.dto.ProductDepartmentResponse;
import com.letsshop.entity.Department;
import com.letsshop.entity.Product;
import com.letsshop.entity.ProductResponse;
import com.letsshop.repository.ProductRepository;
import com.letsshop.service.DeparmentService;
import com.letsshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    DeparmentService deparmentService;

    @Autowired
    private ProductRepository repository;


//    @PreAuthorize("hasAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/products")
    public List<ProductDTO> getProducts() {

        System.out.println(productService.getProducts());
        List<ProductDTO> tempProducts =  productService.getProducts();

        return tempProducts;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

    public Product addProduct(@RequestBody ProductResponse productResponse) {

        System.out.println(productResponse.toString());

        Product isAlreadyExist = repository.findById(productResponse.getProductId()).orElse(null);
        if(isAlreadyExist != null){
            isAlreadyExist.setDeleteOrNo(0);
            return repository.save(isAlreadyExist);
        }

        Product tempProduct = new Product(productResponse.getProductName(), randomPrice());

        Department isDeptExist = deparmentService.getDepartmentByName(productResponse.getDeptName());
        if(isDeptExist != null){
            isDeptExist.add(tempProduct);
            deparmentService.addDepartment(isDeptExist);
            return tempProduct;
        }

        Department tempDepartment = new Department(productResponse.getDeptName());
        tempDepartment.add(tempProduct);

        System.out.println(tempDepartment.toString());


        deparmentService.addDepartment(tempDepartment);
        return tempProduct;

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable int productId, @RequestBody ProductResponse productResponse){

        System.out.println(productResponse.getProductName() + productResponse.getDeptName());
        System.out.println(productId + "from path variable");



        Product tempProduct = repository.findById(productId).get();
        System.out.println(tempProduct.toString());

        tempProduct.setProductName(productResponse.getProductName());

        return repository.save(tempProduct);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/products/{productId}")
    public Product deleteProduct(@PathVariable String productId){
//        String productName = productResponse.getProductName();
        System.out.println("Reached the controller deleting method");
        List<Product> tempProducts = productService.getProductsForDeleting();

        Product productToDelete = tempProducts.stream()
                .filter(p -> p.getProductId() == Integer.parseInt(productId))
                .findFirst()
                .orElse(null);

//        System.out.println(productToDelete.getProductId() + " " + productToDelete.getProductName());
        if( productToDelete != null ){
            productToDelete.setDeleteOrNo(1);

        }

        return  repository.save(productToDelete);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/productsAndDepartments")
    public List<ProductDepartmentResponse> getProductsAndDepartments(){
        return repository.getProductsAndDepartments();
    }




    public static int randomPrice(){
        Random random = new Random();
        int min = 0; int max = 1000;
        return random.nextInt(max - min + 1) + min;

    }

}

