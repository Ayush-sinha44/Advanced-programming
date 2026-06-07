package com.ecommerce.order;

import com.ecommerce.model.Order;

public class DiscountedOrderFactory implements OrderFactory {

    private final double discountRate;  // e.g. 0.10 = 10%

    public DiscountedOrderFactory(double discountRate) {
        if (discountRate < 0 || discountRate >= 1) {
            throw new IllegalArgumentException(
                    "Discount rate must be between 0.0 and 1.0 (e.g. 0.10 for 10%)");
        }
        this.discountRate = discountRate;
    }

    @Override
    public Order createOrder(String orderId, String customerId, double amount) {
        double discountAmount   = amount * discountRate;
        double discountedAmount = amount - discountAmount;

        System.out.println("-----------------------------");
        System.out.println("[DiscountedOrder] Creating discounted order...");
        System.out.printf("[DiscountedOrder] Order ID         : %s%n", orderId);
        System.out.printf("[DiscountedOrder] Customer         : %s%n", customerId);
        System.out.printf("[DiscountedOrder] Original Amount  : ₹%.2f%n", amount);
        System.out.printf("[DiscountedOrder] Discount Rate    : %.0f%%%n", discountRate * 100);
        System.out.printf("[DiscountedOrder] Discount Savings : ₹%.2f%n", discountAmount);
        System.out.printf("[DiscountedOrder] Final Amount     : ₹%.2f%n", discountedAmount);
        System.out.println("-----------------------------");

        return new Order(orderId, customerId, discountedAmount, "DISCOUNTED");
    }
}