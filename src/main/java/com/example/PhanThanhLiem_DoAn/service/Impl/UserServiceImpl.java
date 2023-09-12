package com.example.PhanThanhLiem_DoAn.service.Impl;

import com.example.PhanThanhLiem_DoAn.Dto.UserDto;
import com.example.PhanThanhLiem_DoAn.model.Role;
import com.example.PhanThanhLiem_DoAn.model.User;
import com.example.PhanThanhLiem_DoAn.repository.RoleRepository;
import com.example.PhanThanhLiem_DoAn.repository.UserRepository;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null.");
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setCreatedTime(new Date());
        user.setEnabled(false);

        Role customerRole = roleRepository.findByName("ROLE_CUSTOMER");
        if (customerRole != null) {
            user.setRoles(Arrays.asList(customerRole));
        }

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);

        return userRepository.save(user);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String senderName = "Your company name";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("truongta.mt34@gmail.com", senderName);
        helper.setTo(user.getUsername());
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();


        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        // Thêm lệnh log để kiểm tra mã xác minh và URL đã được tạo và gửi trong email
        System.out.println("Verification Code: " + user.getVerificationCode());
        System.out.println("Verification URL: " + verifyURL);
    }

    @Override
    public boolean verify(String verificationCode) {
        if (verificationCode == null || verificationCode.isEmpty()) {
            return false;
        }

        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null) {
            return false;
        }

        if (user.isEnabled()) {
            return false;
        }

        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }


    @Override
    public boolean isUserEnabled(String username) {
        User user = userRepository.findByUsername(username);
        return user != null && user.isEnabled();
    }

    @Override
    public User saveInfo(User user) {
        User userSave = userRepository.findByUsername(user.getUsername());
        userSave.setAddress(user.getAddress());
        userSave.setCity(user.getCity());
        userSave.setCountry(user.getCountry());
        userSave.setNumber_phone(user.getNumber_phone());
        return userRepository.save(userSave);
    }
}
