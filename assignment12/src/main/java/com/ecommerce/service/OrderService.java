package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.notification.NotificationSender;
import com.ecommerce.payment.PaymentProcessor;
import com.ecommerce.repository.OrderRepository;

import java.util.List;

public class OrderService {

    private final OrderRepository repository;
    private final PaymentProcessor paymentProcessor;
    private final NotificationSender notificationSender;

    public OrderService(OrderRepository repository,
                        PaymentProcessor paymentProcessor,
                        NotificationSender notificationSender) {

        this.repository = repository;
        this.paymentProcessor = paymentProcessor;
        this.notificationSender = notificationSender;
    }

    // ── Process an Order ──
    public void placeOrder(Order order) {

        System.out.println("\n====================================");
        System.out.println("        ORDER PROCESS START         ");
        System.out.println("====================================");

        // Step 1: Process Payment
        boolean paymentSuccess = paymentProcessor.process(order);

        // Step 2: Update Status
        if (paymentSuccess) {
            order.setStatus("PAID");
        } else {
            order.setStatus("FAILED");
        }

        // Step 3: Save Order
        repository.save(order);

        // Step 4: Send Notification
        String message;

        if (paymentSuccess) {
            message = "Your payment was successful. Order confirmed!";
        } else {
            message = "Payment failed. Please try again.";
        }

        notificationSender.send(order, message);

        System.out.println("====================================");
        System.out.println("         ORDER PROCESS END          ");
        System.out.println("====================================\n");
    }

    // ── Find Order ──
    public Order getOrderById(String orderId) {
        return repository.findById(orderId);
    }

    // ── Show All Orders ──
    public void showAllOrders() {
        List<Order> orders = repository.findAll();

        System.out.println("\n========== ALL ORDERS ==========");

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }

        System.out.println("==================================\n");
    }

    // ── Delete Order ──
    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
    }
}