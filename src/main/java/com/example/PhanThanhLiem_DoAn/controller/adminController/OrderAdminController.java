package com.example.PhanThanhLiem_DoAn.controller.adminController;

import com.example.PhanThanhLiem_DoAn.model.Order;
import com.example.PhanThanhLiem_DoAn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderAdminController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "admin/orders", method = RequestMethod.GET)
    public String getAll(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            List<Order> orderList = orderService.findALlOrders();
            model.addAttribute("orders", orderList);
            return "admin/orders";
        }
    }
    @RequestMapping(value = "accept-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String acceptOrder(@PathVariable("id") Long id, RedirectAttributes attributes, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            orderService.acceptOrder(id);
            attributes.addFlashAttribute("success", "Order Accepted");
            return "redirect:/admin/orders";
        }
    }

    @RequestMapping(value = "cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable("id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            orderService.deleteOrder(id);
            return "redirect:/admin/orders";
        }
    }

}
