package com.letsshop.controller;


import com.letsshop.dto.*;
import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.service.EmailSenderService;
import com.letsshop.service.SignUpService;
import com.letsshop.service.UserProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    private static final Logger logger = LogManager.getLogger(SignUpController.class);


    private  UserInfo userInfo;
    @Autowired
    private SignUpService signUpService;
    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private UserProductService userProductService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailSenderService service;

    String otpForVerifcation;
//    private static final Integer EXPIRE_MINS = 5;
//    private LoadingCache<String, Integer> otpCache;

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
        logger.info("Login successfull for user " + getUsernameFromHeader(header));
       return new RoleResponse(role);

   }

    public String getUsernameFromHeader(String header){
        String base64Credentials = header.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");
        return credentials[0];
    }


    @PostMapping("/sendotp")
    public void sendOtpEmail(@RequestBody EmailRequestForOtp emailRequestForOtp){
        System.out.println("In send otp method");
        String toEmail = emailRequestForOtp.getEmail();
        int otpGenerated = generateOtp();

        logger.info("Inside sendOtpEmail method ");
        logger.info("OTP generated successfully  " + otpGenerated);

        service.sendOtpMail(emailRequestForOtp.getEmail(),"The OTP for you is  " +  String.valueOf(otpGenerated), "OTP For Verification");

        logger.info("OTP email sent successfully ");

        logger.info("Storing the sent OTP in instance variable for verification");
         this.otpForVerifcation = String.valueOf(otpGenerated);

//        session.setAttribute("otpForVerifcation", otpForVerifcation);
    }

    @PostMapping("/forgotpassword")
    public ForgotPasswordResponse forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        System.out.println(forgotPasswordRequest.toString());

        logger.info("Inside forgotPassword() method ");
        logger.info("Getting OTP entered in client side from RequestBody " );
        String otpFromRequest = forgotPasswordRequest.getOtp();
        logger.info("OTP fetched from client successfully " +  forgotPasswordRequest.getOtp());

         String otpForVerification1 =  this.otpForVerifcation;


        String emailForVerification =
                userInfoRepository.
                        findByEmail(forgotPasswordRequest
                                .getEmail())
                        .orElse(null)
                        .getEmail();

        String emailFromRequest = forgotPasswordRequest.getEmail();
        logger.info("Verifying OTP entered in client side and OTP generated from server side ");
        if(otpForVerification1.equals(otpFromRequest)
                && emailFromRequest != null
                && emailFromRequest.equals(emailForVerification)){
            logger.info("OTP verification success so sending 'true' response to client");
            return new ForgotPasswordResponse("true");
        }
        logger.info("OTP verification success so sending 'false' response to client");
        return new ForgotPasswordResponse("false");
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestBody NewPasswordRequest newPasswordRequest ){

        logger.info("Inside resetPassword method ");

        logger.info("Getting userInfo object to save password using email " + newPasswordRequest.getEmail());
        UserInfo userInfo = userInfoRepository.findByEmail(newPasswordRequest.getEmail()).get();

        if(userInfo == null){
            logger.info("userInfo object is null, email not found or user doesn't exist");
        }

        logger.info("Resetting the new password on userInfo object");
        userInfo.setPassword(encoder.encode(newPasswordRequest.getNewPassword()));

        userInfoRepository.save(userInfo);
        logger.info("Password Rest successful");
        return "Password Updated";
    }


    @GetMapping("/getroles")
    public List<UserDTO> getRoles(){
        logger.info("inside getRoles method to return the list of users and their roles");
        return signUpService.getUsers();

    }

    @PostMapping("/changerole/user")
    public String changeRoleToUser(@RequestBody UserDTO userDTO){
        logger.info("In changeRoleToUser method ");
        logger.info(userDTO);
        logger.info("Getting userInfo object to change role");
        UserInfo userInfo = userInfoRepository.findByName(userDTO.getName()).get();
        userInfo.setRoles(userDTO.getRole());
        userInfoRepository.save(userInfo);
        return "Role updated successfully";
    }
    @PostMapping("/changerole/admin")
    public String changeRoleToAdmin(@RequestBody UserDTO userDTO){
        logger.info("In changeRoleToAdmin method ");
        logger.info(userDTO.toString());
        logger.info("Getting userInfo object to change role");
        UserInfo userInfo = userInfoRepository.findByName(userDTO.getName()).get();

        userInfo.setRoles(userDTO.getRole());
        logger.info("After setting the user role " + userInfo.getRoles());
        userInfoRepository.save(userInfo);
        return "Role updated successfully";

    }


    private int generateOtp() {
        Random random = new Random();
        int min = 0; int max = 1000;
        int otp = (random.nextInt(max - min + 1) + min);

        return otp;
    }

}