package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface reviewRepository extends JpaRepository<Review, Long> {
}
