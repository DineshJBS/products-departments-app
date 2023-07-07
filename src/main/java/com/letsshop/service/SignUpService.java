package com.letsshop.service;

import com.letsshop.dto.UserDTO;
import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SignUpService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired(required = true)
    private PasswordEncoder encoder;



    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));

         userInfoRepository.save(userInfo);
         return "USer added to the system";
    }

    public List<UserDTO> getUsers(){
        return userInfoRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public UserDTO convertEntityToDto(UserInfo userInfo){

        UserDTO userDTO = new UserDTO();
        userDTO.setName(userInfo.getName());
        userDTO.setRole(userInfo.getRoles());

        return userDTO;
    }

}
