package com.ecommerce.order;

import com.ecommerce.model.Order;

public class RegularOrderFactory implements OrderFactory {

    @Override
    public Order createOrder(String orderId, String customerId, double amount) {
        System.out.println("-----------------------------");
        System.out.println("[RegularOrder] Creating regular order...");
        System.out.printf("[RegularOrder] Order ID  : %s%n", orderId);
        System.out.printf("[RegularOrder] Customer  : %s%n", customerId);
        System.out.printf("[RegularOrder] Amount    : ₹%.2f%n", amount);
        System.out.println("[RegularOrder] No discount or priority fee applied.");
        System.out.println("-----------------------------");

        return new Order(orderId, customerId, amount, "REGULAR");
    }
}