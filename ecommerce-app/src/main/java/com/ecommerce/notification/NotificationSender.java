package com.ecommerce.notification;

import com.ecommerce.model.Order;

public interface NotificationSender {
    void send(Order order, String message);
}