package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.dto.UserDto;
import jakarta.mail.MessagingException;
import com.example.PhanThanhLiem_DoAn.model.User;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;
public interface UserService {
    User findByUsername(String username);
    User save(UserDto userDto);
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
    boolean verify(String verificationCode);
    User saveInfo(User user);
    boolean isUserEnabled(String username);
    Page<User> pageProducts(int pageNo, int pageSize);

    void enableById(Long id);

    void deleteById(Long id);

    Page<User> searchCustomer(int pageNo,String keyword);
}
