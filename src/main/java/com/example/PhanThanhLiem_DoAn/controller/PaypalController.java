package com.example.PhanThanhLiem_DoAn.controller;

import com.example.PhanThanhLiem_DoAn.model.Order;
import com.example.PhanThanhLiem_DoAn.model.OrderDetail;
import com.example.PhanThanhLiem_DoAn.model.ShoppingCart;
import com.example.PhanThanhLiem_DoAn.model.User;
import com.example.PhanThanhLiem_DoAn.service.OrderService;
import com.example.PhanThanhLiem_DoAn.service.PaypalService;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PaypalController {

    @Autowired
    private PaypalService paypalService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @PostMapping("/create-payment")
    @ResponseBody
    public ResponseEntity<String> createPayment(
            @RequestParam("total") Double total,
            @RequestParam("currency") String currency) {
        try {
            String cancelUrl = "http://localhost:8080/cancel-payment";
            String successUrl = "http://localhost:8080/success-payment";


            Payment payment = paypalService.createPayment(
                    total, currency, "paypal", "sale", "Payment description", cancelUrl, successUrl);

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok().body(link.getHref()); // Trả về URL chấp thuận
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    // Helper method to get the base URL of the application
    private String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL().toString().replace(request.getRequestURI(), "");
    }

    @GetMapping("/cancel-payment")
    public String cancelPayment() {
        return "checkout";
    }

    @GetMapping("/success-payment")
    public String successPayment(
            @ModelAttribute("paymentMethod")String paymentMethod,
            Principal principal,
            Model model) {

        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();
        Order newOrder = orderService.saveOrder(shoppingCart,"PayPal");
        model.addAttribute("orders", newOrder);
        orderService.sendOrderConfirmationEmail(user);

//        if (newOrder.getPaymentMethod().equals("PayPal")) {
//            newOrder.setPaymentMethod("Paid via PayPal");
//        } else {
//            newOrder.setPaymentMethod("Cash");
//        }
        model.addAttribute("orderConfirmationMessage", "Your order has been successfully placed. An email confirmation has been sent to your email address.");
//        paypalService.save(newOrder,user);
        return "redirect:/customer/order";
    }

    private static class PaymentResponse {
        private String approvalUrl;

        public PaymentResponse(String approvalUrl) {
            this.approvalUrl = approvalUrl;
        }

        public String getApprovalUrl() {
            return approvalUrl;
        }
    }
}
//sb-oep47z27744531@personal.example.com
//}F|q_5T*
