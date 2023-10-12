package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.model.Review;

import java.util.List;

public interface ReviewService {
    void addNewReview(Review review, ProductDto productId, String username);

    String getAvgRating(Long id);

//    List<Review> getReviewByProduct();
}
