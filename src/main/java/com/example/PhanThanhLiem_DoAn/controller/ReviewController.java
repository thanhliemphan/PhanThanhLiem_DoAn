package com.example.PhanThanhLiem_DoAn.controller;

import com.example.PhanThanhLiem_DoAn.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewController {
    @Autowired
    ReviewService reviewService;

}
