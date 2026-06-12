package com.ecommerce.notification;

import com.ecommerce.model.Order;

public class EmailNotificationSender implements NotificationSender {

    @Override
    public void send(Order order, String message) {
        System.out.println("-----------------------------");
        System.out.println("[Email] Sending email notification...");
        System.out.printf("[Email] To        : %s@ecommerce.com%n", order.getCustomerId());
        System.out.printf("[Email] Subject   : Order %s Confirmation%n", order.getOrderId());
        System.out.printf("[Email] Message   : %s%n", message);
        System.out.printf("[Email] Order Type: %s%n", order.getType());
        System.out.printf("[Email] Amount    : ₹%.2f%n", order.getAmount());
        System.out.printf("[Email] Status    : %s%n", order.getStatus());
        System.out.println("[Email] Notification sent successfully.");
        System.out.println("-----------------------------");
    }
}