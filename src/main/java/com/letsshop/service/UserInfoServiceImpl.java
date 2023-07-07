package com.letsshop.service;

import com.letsshop.dto.ProductDTO;
import com.letsshop.dto.UserDTO;
import com.letsshop.entity.Product;
import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class UserInfoServiceImpl implements UserDetailsService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        // Convert the User entity to a UserDetails object
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(userInfo.getUsername())
//                .password(userInfo.getPassword())
//                .roles("USER") // Assign user roles as needed
//                .build();
        return null;
    }


}
