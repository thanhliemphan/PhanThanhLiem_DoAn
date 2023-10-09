package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Review;

public interface ReviewService {
    void addNewReview(Review review, ProductDto productId, String username);
}
