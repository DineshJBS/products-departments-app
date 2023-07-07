package com.letsshop.service;

import com.letsshop.dto.ProductDTO;
import com.letsshop.entity.Product;
import com.letsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceimpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    private ProductDTO productDTO;
    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getDeleteOrNo() == 0)
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsForDeleting() {
        return productRepository.findAll();

    }


    @Override
    public void deleteProductById(int productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(Product productToDelete) {
         productRepository.delete(productToDelete);
    }

    @Override
    public Product findByProductName(String productName) {
        System.out.println("from Product Service imple  " + productRepository.findByProductName(productName) );
        return productRepository.findByProductName(productName);
    }

    public ProductDTO convertEntityToDto(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
//        productDTO.setDeptName(product.getDepartment().getDeptName());

        return productDTO;
    }

}
