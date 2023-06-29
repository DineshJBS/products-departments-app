package com.letsshop.service;

import com.letsshop.dto.ProductDTO;
import com.letsshop.entity.Product;
import java.util.*;

public interface ProductService {

    public List<ProductDTO> getProducts();


    Product addProduct(Product product);

    public void deleteProduct(Product productToDelete);

    Product findByProductName(String productName);

    public List<Product> getProductsForDeleting();

    void deleteProductById(int productId);
}
