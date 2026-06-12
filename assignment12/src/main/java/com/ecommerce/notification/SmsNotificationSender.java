package com.ecommerce.notification;

import com.ecommerce.model.Order;

public class SmsNotificationSender implements NotificationSender {

    @Override
    public void send(Order order, String message) {
        System.out.println("-----------------------------");
        System.out.println("[SMS] Sending SMS notification...");
        System.out.printf("[SMS] To       : +91-XXXXXX%s%n",
                order.getCustomerId().replaceAll("[^0-9]", ""));
        System.out.printf("[SMS] Order ID : %s%n", order.getOrderId());
        System.out.printf("[SMS] Message  : %s%n", message);
        System.out.printf("[SMS] Status   : %s%n", order.getStatus());
        System.out.println("[SMS] Notification sent successfully.");
        System.out.println("-----------------------------");
    }
}