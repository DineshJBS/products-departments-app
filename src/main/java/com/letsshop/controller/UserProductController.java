package com.letsshop.controller;

import com.google.gson.Gson;
import com.letsshop.dto.ProductDTO;
import com.letsshop.dto.UserProductRequest;
import com.letsshop.entity.UserInfo;
import com.letsshop.entity.UserProduct;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.repository.UserProductRepository;
import com.letsshop.service.ProductService;
import com.letsshop.service.UserProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RestController
public class UserProductController {

    @Autowired
    private HttpSession session;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;


    @Autowired(required = true)
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @GetMapping("/user-products")
    public List<ProductDTO> getProductsForUser(){

        return productService.getProducts();

    }

    @PostMapping("/add-to-cart")
    public String addProductToCart(@RequestBody List<UserProductRequest> userProductRequestList, @RequestHeader("Authorization") String header){

        System.out.println("/add-t--cart");

       userProductRequestList.forEach(userProductRequest -> {

           System.out.println(userProductRequest.toString());

           int currentUserId = getCurrentUserId(header);
           System.out.println(currentUserId);


           LocalDate date = LocalDate.now();
           DateTimeFormatter formattedDate =
                   DateTimeFormatter.ofPattern("dd-MM-yyy");
           String stringDate = date.format(formattedDate);
           UserProduct tempUserProduct =
                   new UserProduct(currentUserId, userProductRequest.getProductName(), userProductRequest.getPrice(), stringDate);
           System.out.println(tempUserProduct.toString());

           userProductService.addProductToCart(tempUserProduct);

       });

         return "Products added to DB";

    }

    @GetMapping("/user-orders")
    public List<UserProduct> getOrders(@RequestHeader("Authorization") String header ){

        int currentUserId = getCurrentUserId(header);

        List<UserProduct> ordersOfThisUser = userProductRepository.findAllByUserId(currentUserId);

        System.out.println(ordersOfThisUser);
        return ordersOfThisUser;

    }


    public int getCurrentUserId(String header){

        String currentUserName = getUsernameFromHeader(header);
        System.out.println(currentUserName);
        UserInfo currentUser = userInfoRepository.findByName(currentUserName).orElse(null);

        int currentUserId = currentUser.getId();
        return currentUserId;
    }

    public String getUsernameFromHeader(String header){
        String base64Credentials = header.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");
        return credentials[0];
    }


}
