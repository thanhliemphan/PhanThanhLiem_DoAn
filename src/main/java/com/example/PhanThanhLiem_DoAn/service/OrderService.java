package com.example.PhanThanhLiem_DoAn.service;
import com.example.PhanThanhLiem_DoAn.model.Order;
import com.example.PhanThanhLiem_DoAn.model.ShoppingCart;
import com.example.PhanThanhLiem_DoAn.model.User;

import java.util.List;

public interface OrderService {
    Order saveOrder(ShoppingCart shoppingCart);

    List<Order> findALlOrders();

    List<Order> findAll(String username);

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

    void sendOrderConfirmationEmail(User user);
    Order updatePaymentMethod(Order order);
    void deleteOrder(Long id);
}
