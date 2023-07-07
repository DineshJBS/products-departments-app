package com.letsshop.service;

import com.letsshop.dto.UserDTO;
import com.letsshop.dto.UserProductRequest;
import com.letsshop.entity.UserProduct;

import java.util.List;

public interface UserProductService {

    public UserProduct addProductToCart(UserProduct userProduct);

}
