package com.ecommerce.order;

import com.ecommerce.model.Order;

public interface OrderFactory {
    Order createOrder(String orderId, String customerId, double amount);
}