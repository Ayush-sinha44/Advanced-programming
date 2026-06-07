package com.ecommerce.repository;

import com.ecommerce.model.Order;
import java.util.List;

public interface OrderRepository {
    void save(Order order);
    Order findById(String orderId);
    List<Order> findAll();
    void deleteById(String orderId);
}