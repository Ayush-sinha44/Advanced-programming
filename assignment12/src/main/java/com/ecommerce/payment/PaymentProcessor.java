package com.ecommerce.payment;

import com.ecommerce.model.Order;

public interface PaymentProcessor {
    boolean process(Order order);
}
