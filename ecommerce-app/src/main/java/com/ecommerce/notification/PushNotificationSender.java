package com.ecommerce.notification;

import com.ecommerce.model.Order;

public class PushNotificationSender implements NotificationSender {

    @Override
    public void send(Order order, String message) {
        System.out.println("-----------------------------");
        System.out.println("[Push] Sending push notification...");
        System.out.printf("[Push] Device    : %s's mobile device%n", order.getCustomerId());
        System.out.printf("[Push] Title     : Order Update 🛒%n");
        System.out.printf("[Push] Order ID  : %s%n", order.getOrderId());
        System.out.printf("[Push] Message   : %s%n", message);
        System.out.printf("[Push] Status    : %s%n", order.getStatus());
        System.out.println("[Push] Notification sent successfully.");
        System.out.println("-----------------------------");
    }
}