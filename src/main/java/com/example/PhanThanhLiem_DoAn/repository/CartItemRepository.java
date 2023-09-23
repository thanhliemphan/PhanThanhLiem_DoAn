package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Modifying
    @Query("delete from CartItem c where c.cart.id= ?1")
    void deleteByCartId(Long cartId);
}
