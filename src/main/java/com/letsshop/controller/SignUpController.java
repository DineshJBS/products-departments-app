package com.letsshop.controller;

import com.letsshop.entity.UserInfo;
import com.letsshop.response.LoginResponse;
import com.letsshop.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:4200"})
public class SignUpController {


    @Autowired
    private SignUpService signUpService;

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping("/signup-submit")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo.getName() + " " + userInfo.getEmail() + " " + userInfo.getPassword() + userInfo.getRoles());

        return signUpService.addUser(userInfo);
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfo userInfo) {
        // Your existing code...
        System.out.println("login url hit");

        return ResponseEntity.ok("OK");
    }

}