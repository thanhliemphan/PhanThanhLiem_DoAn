package com.example.PhanThanhLiem_DoAn.controller;

import com.example.PhanThanhLiem_DoAn.model.*;
import com.example.PhanThanhLiem_DoAn.service.OrderService;
import com.example.PhanThanhLiem_DoAn.service.ProductService;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.internal.util.StringHelper.isBlank;

@Controller
public class OrderController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @RequestMapping(value = "/customer/check-out", method = RequestMethod.GET)
    public String checkout(Model model, Principal principal, RedirectAttributes attributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);

        if (isBlank(user.getNumber_phone()) || isBlank(user.getAddress())
                || isBlank(user.getCity()) || isBlank(user.getCountry())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "you must fill the information after checkout!");
            return "account";
        } else {
            model.addAttribute("user", user);
            ShoppingCart shoppingCart = user.getShoppingCart();
            if (shoppingCart == null) {
                return "redirect:/customer/cart";
            }
            List<String> insufficientStockMessages = new ArrayList<>();

            for (CartItem cartItem : shoppingCart.getCartItems()) {
                Product product = cartItem.getProduct();
                int requestedQuantity = cartItem.getQuantity();
                int availableQuantity = product.getCurrentQuantity();

                if (requestedQuantity > availableQuantity) {
                    insufficientStockMessages.add("Insufficient stock for product: " + product.getName());
                }
            }

            if (!insufficientStockMessages.isEmpty()) {
                attributes.addFlashAttribute("insufficientStockMessages", insufficientStockMessages);
                return "redirect:/customer/cart";
            }
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "checkout";
    }

    @RequestMapping(value = "customer/order",method = RequestMethod.GET)
    public String order(Principal principal,Model model){
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        List<Order> orderList = user.getOrders();
        model.addAttribute("orders", orderList);
        return "order";
    }
    @RequestMapping(value = "save-order",method = RequestMethod.GET)
    public String saveOrder(Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        ShoppingCart shoppingCart = user.getShoppingCart();
        orderService.saveOrder(shoppingCart);

        return "redirect:/customer/order";
    }
    @RequestMapping(value = "/cancelorder/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancel(@PathVariable("id") Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Cancel order successfully!");
        return "redirect:/customer/order";
    }
}
