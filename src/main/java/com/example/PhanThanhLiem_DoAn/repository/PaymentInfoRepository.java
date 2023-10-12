package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo,Long> {
}
