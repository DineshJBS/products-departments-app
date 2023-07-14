package com.letsshop.controller;

import com.letsshop.dto.Orders;
import com.letsshop.entity.UserInfo;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.repository.UserProductRepository;
import com.letsshop.service.OrdersExcelExporter;
import com.letsshop.service.UserProductService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private UserProductService userProductService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    private static final Logger logger = LogManager.getLogger(InvoiceController.class);


    @GetMapping("/invoice")
    public List<Orders> getOrders(@RequestHeader("Authorization") String header){
        int currentUserId = getCurrentUserId(header);
        logger.info("Inside getOrders method " + "for userId " + currentUserId);
        logger.info("Orders before returning to client " + userProductService.getOrders(currentUserId) );
        return userProductService.getOrders(currentUserId);
    }


    public int getCurrentUserId(String header){

        String currentUserName = getUsernameFromHeader(header);
        System.out.println(currentUserName);
        UserInfo currentUser = userInfoRepository.findByName(currentUserName).orElse(null);

        int currentUserId = currentUser.getId();
        return currentUserId;
    }

    public String getUsernameFromHeader(String header){
        String base64Credentials = header.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] credentials = decodedCredentials.split(":");
        return credentials[0];
    }


    @GetMapping("/orders/export/excel")
    public void exportToExcel(HttpServletResponse response , @RequestHeader("Authorization") String header) throws IOException, MessagingException {

        logger.info("Inside exportToExcel method " ) ;

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = getUsernameFromHeader(header) +"_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Orders> orders = userProductService.getOrders(getCurrentUserId(header));
        OrdersExcelExporter excelExporter = new OrdersExcelExporter(orders);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        excelExporter.export(outputStream);
        byte[] excelData = outputStream.toByteArray();


        String currentUserName = getUsernameFromHeader(header);
        String email = userInfoRepository.findByName(currentUserName).get().getEmail();
        String attachmentName = getUsernameFromHeader(header) + "_" + currentDateTime + ".xlsx";


        String recipientEmail = email;
        String subject = "Orders Excel Export";
        String body = "Please find your orders Excel file attached to this email.";

//        emailService.sendEmailWithAttachment(recipientEmail, subject, body, excelData, attachmentName);
        logger.info("Calling sendEmailWithAttachment method in userProductService to trigger email " ) ;
        userProductService.sendEmailWithAttachment(recipientEmail, subject, body, excelData, attachmentName);

//        excelExporter.export(response);
//        response.flushBuffer();

    }

}
