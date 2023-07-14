package com.letsshop.controller;
import com.letsshop.dto.ProductDTO;
import com.letsshop.dto.ProductDepartmentResponse;
import com.letsshop.entity.Department;
import com.letsshop.entity.Product;
import com.letsshop.entity.ProductResponse;
import com.letsshop.repository.ProductRepository;
import com.letsshop.service.DeparmentService;
import com.letsshop.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(ProductController.class);


    //    @PreAuthorize("hasAuthority('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/products")
    public List<ProductDTO> getProducts() {

        logger.info(" Getting list of products before returning " + productService.getProducts());
        List<ProductDTO> tempProducts =  productService.getProducts();

        return tempProducts;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

    public Product addProduct(@RequestBody ProductResponse productResponse) {

        logger.info(" Inside addProduct method ");
        logger.info(" Incoming request  " + productResponse.toString());
//        System.out.println(productResponse.toString());

        logger.info(" Checking in DB if the product exists already ");
        Product isAlreadyExist = repository.findById(productResponse.getProductId()).orElse(null);
        if(isAlreadyExist != null){
            logger.info(" Product already exist and undoing the soft delete");
            isAlreadyExist.setDeleteOrNo(0);
            return repository.save(isAlreadyExist);
        }
        logger.info(" Product doesn't exist and so creating a new product object");
        Product tempProduct = new Product(productResponse.getProductName(), randomPrice());
        logger.info("the new product " + tempProduct.toString());

        logger.info(" Now checking if department exists already ");
        Department isDeptExist = deparmentService.getDepartmentByName(productResponse.getDeptName());
        if(isDeptExist != null){
            logger.info(" Department exists already so now adding the new product to the department ");
            isDeptExist.add(tempProduct);
            logger.info(" Modified department object before saving " + isDeptExist.toString());
            deparmentService.addDepartment(isDeptExist);
            return tempProduct;
        }

        logger.info(" Department doesn't exist in database, so now creating a new department");
        Department tempDepartment = new Department(productResponse.getDeptName());
        tempDepartment.add(tempProduct);

        logger.info(" New department before saving to database  " + tempDepartment.toString());

        deparmentService.addDepartment(tempDepartment);
        return tempProduct;

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable int productId, @RequestBody ProductResponse productResponse){

        logger.info(" Inside updateProduct method ");
        logger.info("Incoming Product request for updating" +  productResponse.toString());
        logger.info(productId + "from path variable");



        Product tempProduct = repository.findById(productId).get();
        logger.info("The product before updating " + tempProduct.toString());

        tempProduct.setProductName(productResponse.getProductName());
        logger.info("The product after updating " + tempProduct.toString());

        return repository.save(tempProduct);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/products/{productId}")
    public Product deleteProduct(@PathVariable String productId){
//        String productName = productResponse.getProductName();
        logger.info("Reached the controller deleting method");
        List<Product> tempProducts = productService.getProductsForDeleting();
        logger.info("The product before deleting " + tempProducts.toString());
        Product productToDelete = tempProducts.stream()
                .filter(p -> p.getProductId() == Integer.parseInt(productId))
                .findFirst()
                .orElse(null);
        if (productToDelete != null) {
            logger.info("The product after deleting " + productToDelete.toString());
        }else{
            logger.info("The product not found");
        }
//        System.out.println(productToDelete.getProductId() + " " + productToDelete.getProductName());
        if( productToDelete != null ){
            productToDelete.setDeleteOrNo(1);

        }

        return  repository.save(productToDelete);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/productsAndDepartments")
    public List<ProductDepartmentResponse> getProductsAndDepartments(){
        logger.info("Inside getProductsAndDepartment method , Response before returning to client " + repository.getProductsAndDepartments());
        return repository.getProductsAndDepartments();
    }




    public static int randomPrice(){
        Random random = new Random();
        int min = 0; int max = 1000;
        return random.nextInt(max - min + 1) + min;

    }

}

