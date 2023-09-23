package com.example.PhanThanhLiem_DoAn.repository;
import com.example.PhanThanhLiem_DoAn.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

}
