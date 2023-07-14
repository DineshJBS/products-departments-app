package com.letsshop.service;

import com.letsshop.dto.Orders;
import com.letsshop.dto.UserDTO;
import com.letsshop.dto.UserProductRequest;
import com.letsshop.entity.UserProduct;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserProductService {

    public UserProduct addProductToCart(UserProduct userProduct);

    public List<Orders> getOrders(int currentUserId);
   public void sendEmailWithAttachment(String recipientEmail,String subject,String body,byte[] excelData,String attachmentName) throws MessagingException;
}
