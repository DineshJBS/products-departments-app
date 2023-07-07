package com.letsshop.controller;

import com.letsshop.dto.*;
import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.service.SignUpService;
import com.letsshop.service.UserProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = {"http://localhost:4200"})
public class SignUpController {

    private  UserInfo userInfo;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private PasswordEncoder encoder;

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping("/signup-submit")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo.getName() + " " + userInfo.getEmail() + " " + userInfo.getPassword() + userInfo.getRoles());

        return signUpService.addUser(userInfo);
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInfo userInfo) {

//        session.setMaxInactiveInterval(1800);
        this.userInfo = userInfo;
        System.out.println(userInfo.toString());
        System.out.println("login url hit");

        return ResponseEntity.ok("OK");
    }

   @GetMapping("/role")
    public RoleResponse getRole(@RequestHeader("Authorization") String header){
       String role = userInfoRepository
               .findByName(getUsernameFromHeader(header))
               .get()
               .getRoles();

       return new RoleResponse(role);

   }

    public String getUsernameFromHeader(String header){
        String base64Credentials = header.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");
        return credentials[0];
    }

    @PostMapping("/forgotpassword")
    public ForgotPasswordResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        System.out.println(forgotPasswordRequest.toString());

        String otpFromRequest = forgotPasswordRequest.getOtp();
        String otpForVerifcation = String.valueOf(generateOtp());

        String emailForVerification =
                userInfoRepository.
                        findByEmail(forgotPasswordRequest
                                .getEmail())
                        .orElse(null)
                        .getEmail();

        String emailFromRequest = forgotPasswordRequest.getEmail();

        if(otpForVerifcation.equals(otpFromRequest)
                && emailFromRequest != null
                && emailFromRequest.equals(emailForVerification)){
            return new ForgotPasswordResponse("true");
        }

        return new ForgotPasswordResponse("false");
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestBody NewPasswordRequest newPasswordRequest){


        UserInfo userInfo = userInfoRepository.findByEmail(newPasswordRequest.getEmail()).get();

        userInfo.setPassword(encoder.encode(newPasswordRequest.getNewPassword()));

        userInfoRepository.save(userInfo);
        return "Password Updated";
    }


    @GetMapping("/getroles")
    public List<UserDTO> getRoles(){
        return signUpService.getUsers();

    }

    @PostMapping("/changerole/user")
    public String changeRoleToUser(@RequestBody UserDTO userDTO){
        System.out.println("in user method");
        System.out.println(userDTO.toString());
        UserInfo userInfo = userInfoRepository.findByName(userDTO.getName()).get();
        userInfo.setRoles(userDTO.getRole());
        userInfoRepository.save(userInfo);
        return "Role updated successfully";
    }
    @PostMapping("/changerole/admin")
    public String changeRoleToAdmin(@RequestBody UserDTO userDTO){
        System.out.println(userDTO.toString());
        UserInfo userInfo = userInfoRepository.findByName(userDTO.getName()).get();
        System.out.println(userInfo.toString());
        userInfo.setRoles(userDTO.getRole());
        System.out.println("After setting the user role " + userInfo.getRoles());
        userInfoRepository.save(userInfo);
        return "Role updated successfully";

    }





    private int generateOtp() {
//        Random random = new Random();
//        int min = 0; int max = 1000;
//        return (random.nextInt(max - min + 1) + min);
        return 123;
    }


}