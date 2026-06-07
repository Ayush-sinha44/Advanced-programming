package com.ecommerce.model;

// SRP: pure data carrier, no business logic
public class Order {
    private final String orderId;
    private final String customerId;
    private final double amount;
    private final String type;          // "REGULAR" | "DISCOUNTED" | "PRIORITY"
    private String status;              // "PENDING" | "PAID" | "FAILED"

    public Order(String orderId, String customerId, double amount, String type) {
        this.orderId    = orderId;
        this.customerId = customerId;
        this.amount     = amount;
        this.type       = type;
        this.status     = "PENDING";
    }

    // Getters
    public String getOrderId()    { return orderId; }
    public String getCustomerId() { return customerId; }
    public double getAmount()     { return amount; }
    public String getType()       { return type; }
    public String getStatus()     { return status; }
    public void   setStatus(String s) { this.status = s; }

    @Override
    public String toString() {
        return String.format("Order[id=%s, customer=%s, type=%s, amount=%.2f, status=%s]",
                orderId, customerId, type, amount, status);
    }
}
