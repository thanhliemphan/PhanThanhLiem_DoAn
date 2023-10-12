package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(rating)  FROM Review r inner join Product p on r.product.id = ?1 and p.id = ?1")
    Double getAvgRatingByProduct(Long id);
//    @Query("select r from Review r inner join Product p on p.id = r.product.id where p.id = ?1")
//    List<Review> getProductsInCategory(Long productId);
}
