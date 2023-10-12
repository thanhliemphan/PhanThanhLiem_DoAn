package com.example.PhanThanhLiem_DoAn.service.Impl;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.model.Review;
import com.example.PhanThanhLiem_DoAn.model.User;
import com.example.PhanThanhLiem_DoAn.repository.ReviewRepository;
import com.example.PhanThanhLiem_DoAn.service.ReviewService;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    UserService userService;
    @Autowired
    ReviewRepository reviewRepository;
    @Override
    public void addNewReview(Review review, ProductDto productDto, String username) {
        User user = userService.findByUsername(username);
        Product product = transfer(productDto);
        review.setProduct(product);
        review.setUser(user);
        review.setCreatedTime(new Date());
        reviewRepository.save(review);
    }

    @Override
    public double getAvgRating(Long id) {
        double avgRating = reviewRepository.getAvgRatingByProduct(id);
        return avgRating;
    }

    private Product transfer(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.set_activated(productDto.isActivated());
        product.set_deleted(productDto.isDeleted());
        product.setCategory(productDto.getCategory());
        return product;
    }

}
