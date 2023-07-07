package com.letsshop.service;

import com.letsshop.dto.UserDTO;
import com.letsshop.dto.UserProductRequest;
import com.letsshop.entity.UserInfo;
import com.letsshop.entity.UserProduct;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductServiceImpl implements UserProductService{

    @Autowired
    private UserProductRepository userProductRepository;

    @Override
    public UserProduct addProductToCart(UserProduct userProduct) {
        return userProductRepository.save(userProduct);
    }


}
