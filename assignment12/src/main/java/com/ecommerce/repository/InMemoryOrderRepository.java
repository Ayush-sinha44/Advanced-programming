package com.ecommerce.repository;

import com.ecommerce.model.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> store = new HashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.getOrderId(), order);
        System.out.println("-----------------------------");
        System.out.println("[InMemoryDB] Order saved successfully.");
        System.out.printf("[InMemoryDB] Order ID  : %s%n", order.getOrderId());
        System.out.printf("[InMemoryDB] Customer  : %s%n", order.getCustomerId());
        System.out.printf("[InMemoryDB] Type      : %s%n", order.getType());
        System.out.printf("[InMemoryDB] Amount    : ₹%.2f%n", order.getAmount());
        System.out.printf("[InMemoryDB] Status    : %s%n", order.getStatus());
        System.out.println("-----------------------------");
    }

    @Override
    public Order findById(String orderId) {
        Order order = store.get(orderId);
        if (order != null) {
            System.out.printf("[InMemoryDB] Found order: %s%n", orderId);
        } else {
            System.out.printf("[InMemoryDB] Order not found: %s%n", orderId);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        System.out.printf("[InMemoryDB] Total orders in store: %d%n", store.size());
        return new ArrayList<>(store.values());
    }

    @Override
    public void deleteById(String orderId) {
        if (store.containsKey(orderId)) {
            store.remove(orderId);
            System.out.printf("[InMemoryDB] Order deleted: %s%n", orderId);
        } else {
            System.out.printf("[InMemoryDB] Cannot delete — order not found: %s%n", orderId);
        }
    }
}