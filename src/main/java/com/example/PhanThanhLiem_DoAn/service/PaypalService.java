package com.example.PhanThanhLiem_DoAn.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import java.text.ParseException;

public interface PaypalService {
    Payment createPayment(Double total, String currency, String paypal, String sale, String paymentDescription, String cancelUrl, String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException, ParseException;

//    void save(Order newOrder, User user);
}
