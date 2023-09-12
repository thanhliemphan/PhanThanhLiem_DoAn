package com.example.PhanThanhLiem_DoAn.controller;

import com.example.PhanThanhLiem_DoAn.Dto.UserDto;
import com.example.PhanThanhLiem_DoAn.Utils.Utility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.example.PhanThanhLiem_DoAn.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.PhanThanhLiem_DoAn.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @RequestMapping("admin/index")
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "admin/index";
    }

    @RequestMapping(value = "register",method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("userDto", new UserDto());

        return "register";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("userDto") UserDto userDto,
                              BindingResult result,
                              Model model, HttpServletRequest request) {

        try {
            if (result.hasErrors()) {
                // Xử lý lỗi nếu cần
                model.addAttribute("userDto", userDto);
                return "register";
            }

            String username = userDto.getUsername();
            User existingUser = userService.findByUsername(username);

            if (existingUser != null) {
                model.addAttribute("userDto", userDto);
                model.addAttribute("emailError", "Your email has been registered!");
                return "register";
            }

            if (userDto.getPassword().equals(userDto.getRepeatPassword())) {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                User savedUser = userService.save(userDto);
                String siteURL = Utility.getSiteURL(request);
                userService.sendVerificationEmail(savedUser, siteURL);
                model.addAttribute("success", "Please enable email for logging in!");
                model.addAttribute("userDto", userDto);
            } else {
                // Xử lý trường hợp mật khẩu không khớp
                model.addAttribute("userDto", userDto);
                model.addAttribute("passwordError", "Your password may be wrong! Check again!");
                return "register";
            }
        } catch (Exception e) {
            // Xử lý lỗi server
            e.printStackTrace();
            model.addAttribute("errors", "The server encountered an error!");
        }

        return "register";
    }
    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code, Model model) {
        if (userService.verify(code)) {
            model.addAttribute("verificationSuccess", "Verification successful!");
            return "redirect:/login";
        } else {
            model.addAttribute("verificationError", "Verification failed.");
            return "redirect:/register";
        }
    }

}
