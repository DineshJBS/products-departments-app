package com.letsshop.service;

import com.letsshop.controller.DepartmentController;
import com.letsshop.dto.Orders;
import com.letsshop.dto.UserDTO;
import com.letsshop.dto.UserProductRequest;
import com.letsshop.entity.UserInfo;
import com.letsshop.entity.UserProduct;
import com.letsshop.repository.UserInfoRepository;
import com.letsshop.repository.UserProductRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductServiceImpl implements UserProductService{

    @Autowired
    private UserProductRepository userProductRepository;
    @Autowired
    private JavaMailSender mailSender;
    private static final Logger logger = LogManager.getLogger(UserProductServiceImpl.class);


    @Override
    public UserProduct addProductToCart(UserProduct userProduct) {
        return userProductRepository.save(userProduct);
    }

    @Override
    public List<Orders> getOrders(int currentUserId) {
        return userProductRepository.findAllByUserId(currentUserId)
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }



    private Orders convertEntityToDTO(UserProduct userProduct) {
        Orders order = new Orders();
        order.setOrderId(userProduct.getOrderId());
        order.setOrderDate(userProduct.getOrderDate());
        order.setProductName(userProduct.getProductName());
        order.setPrice(userProduct.getPrice());
        return order;
    }

    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, byte[] attachment, String attachmentName) throws MessagingException {        MimeMessage message = mailSender.createMimeMessage();

        logger.info("Inside sendEmailWithAttachment method " ) ;
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(body);


        ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        helper.addAttachment(attachmentName, dataSource);

        mailSender.send(message);
        logger.info("Email sent successfully " + "to " + recipientEmail ) ;
    }
}
