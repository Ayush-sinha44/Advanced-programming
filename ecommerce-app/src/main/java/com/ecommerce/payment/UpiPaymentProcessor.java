package com.ecommerce.payment;

import com.ecommerce.model.Order;

public class UpiPaymentProcessor implements PaymentProcessor {

    @Override
    public boolean process(Order order) {
        System.out.println("-----------------------------");
        System.out.println("[UPI] Processing payment...");
        System.out.printf("[UPI] Order ID : %s%n", order.getOrderId());
        System.out.printf("[UPI] Customer  : %s%n", order.getCustomerId());
        System.out.printf("[UPI] Amount    : ₹%.2f%n", order.getAmount());
        System.out.println("[UPI] Status    : SUCCESS");
        System.out.println("-----------------------------");
        return true;
    }
}