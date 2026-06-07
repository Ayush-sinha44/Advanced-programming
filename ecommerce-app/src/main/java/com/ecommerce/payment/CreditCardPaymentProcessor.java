package com.ecommerce.payment;

import com.ecommerce.model.Order;

public class CreditCardPaymentProcessor implements PaymentProcessor {

    @Override
    public boolean process(Order order) {
        System.out.println("-----------------------------");
        System.out.println("[CreditCard] Processing payment...");
        System.out.printf("[CreditCard] Order ID : %s%n", order.getOrderId());
        System.out.printf("[CreditCard] Customer  : %s%n", order.getCustomerId());
        System.out.printf("[CreditCard] Amount    : ₹%.2f%n", order.getAmount());
        System.out.println("[CreditCard] Status    : SUCCESS");
        System.out.println("-----------------------------");
        return true;
    }
}
