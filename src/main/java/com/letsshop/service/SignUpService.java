package com.letsshop.service;

import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder encoder;



    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));

         userInfoRepository.save(userInfo);
         return "USer added to the system";
    }

}
