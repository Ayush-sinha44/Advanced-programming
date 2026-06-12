package com.ecommerce.order;

import com.ecommerce.model.Order;

public class PriorityOrderFactory implements OrderFactory {

    private final double priorityFee;   // flat fee added on top of order amount

    // Default priority fee of ₹50
    public PriorityOrderFactory() {
        this.priorityFee = 50.0;
    }

    // Custom priority fee
    public PriorityOrderFactory(double priorityFee) {
        if (priorityFee < 0) {
            throw new IllegalArgumentException("Priority fee cannot be negative.");
        }
        this.priorityFee = priorityFee;
    }

    @Override
    public Order createOrder(String orderId, String customerId, double amount) {
        double totalAmount = amount + priorityFee;

        System.out.println("-----------------------------");
        System.out.println("[PriorityOrder] Creating priority order...");
        System.out.printf("[PriorityOrder] Order ID       : %s%n", orderId);
        System.out.printf("[PriorityOrder] Customer       : %s%n", customerId);
        System.out.printf("[PriorityOrder] Base Amount    : ₹%.2f%n", amount);
        System.out.printf("[PriorityOrder] Priority Fee   : ₹%.2f%n", priorityFee);
        System.out.printf("[PriorityOrder] Total Amount   : ₹%.2f%n", totalAmount);
        System.out.println("[PriorityOrder] This order will be processed first.");
        System.out.println("-----------------------------");

        return new Order(orderId, customerId, totalAmount, "PRIORITY");
    }
}