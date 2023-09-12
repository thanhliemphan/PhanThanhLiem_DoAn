package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.Dto.UserDto;
import jakarta.mail.MessagingException;
import com.example.PhanThanhLiem_DoAn.model.User;

import java.io.UnsupportedEncodingException;
public interface UserService {
    User findByUsername(String username);
    User save(UserDto userDto);
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
    boolean verify(String verificationCode);
    User saveInfo(User user);
    boolean isUserEnabled(String username);

}
