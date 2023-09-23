package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Order;
import com.example.PhanThanhLiem_DoAn.model.OrderDetail;
import com.example.PhanThanhLiem_DoAn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    OrderDetail findByOrderAndProduct(Order order, Product product);
}
