package com.ecommerce;

import com.ecommerce.model.Order;

import com.ecommerce.notification.*;
import com.ecommerce.order.*;

import com.ecommerce.payment.*;

import com.ecommerce.repository.*;

import com.ecommerce.service.OrderService;

public class Main {

    public static void main(String[] args) {

        // ============================================
        // REPOSITORY
        // ============================================

        // Choose one repository

        OrderRepository repository =
                new InMemoryOrderRepository();

        // Uncomment this if you want file storage
        // OrderRepository repository =
        //         new FileOrderRepository("orders_db");


        // ============================================
        // ORDER FACTORIES
        // ============================================

        OrderFactory regularFactory =
                new RegularOrderFactory();

        OrderFactory discountedFactory =
                new DiscountedOrderFactory(0.10);

        OrderFactory priorityFactory =
                new PriorityOrderFactory(75);


        // ============================================
        // CREATE ORDERS
        // ============================================

        Order order1 =
                regularFactory.createOrder(
                        "ORD-101",
                        "ayush01",
                        1200
                );

        Order order2 =
                discountedFactory.createOrder(
                        "ORD-102",
                        "rahul02",
                        2500
                );

        Order order3 =
                priorityFactory.createOrder(
                        "ORD-103",
                        "priya03",
                        5000
                );


        // ============================================
        // PAYMENT PROCESSORS
        // ============================================

        PaymentProcessor creditCardProcessor =
                new CreditCardPaymentProcessor();

        PaymentProcessor upiProcessor =
                new UpiPaymentProcessor();

        PaymentProcessor walletProcessor =
                new WalletPaymentProcessor(3000);


        // ============================================
        // NOTIFICATION SENDERS
        // ============================================

        NotificationSender emailSender =
                new EmailNotificationSender();

        NotificationSender smsSender =
                new SmsNotificationSender();

        NotificationSender pushSender =
                new PushNotificationSender();


        // ============================================
        // ORDER SERVICES
        // ============================================

        OrderService service1 =
                new OrderService(
                        repository,
                        creditCardProcessor,
                        emailSender
                );

        OrderService service2 =
                new OrderService(
                        repository,
                        upiProcessor,
                        smsSender
                );

        OrderService service3 =
                new OrderService(
                        repository,
                        walletProcessor,
                        pushSender
                );


        // ============================================
        // PLACE ORDERS
        // ============================================

        service1.placeOrder(order1);

        service2.placeOrder(order2);

        service3.placeOrder(order3);


        // ============================================
        // FETCH ORDER
        // ============================================

        System.out.println("\nSearching for ORD-102...\n");

        Order fetchedOrder =
                service1.getOrderById("ORD-102");

        if (fetchedOrder != null) {
            System.out.println("Fetched Order:");
            System.out.println(fetchedOrder);
        }


        // ============================================
        // SHOW ALL ORDERS
        // ============================================

        service1.showAllOrders();


        // ============================================
        // DELETE ORDER
        // ============================================

        System.out.println("Deleting ORD-101...\n");

        service1.deleteOrder("ORD-101");


        // ============================================
        // SHOW ALL ORDERS AGAIN
        // ============================================

        service1.showAllOrders();
    }
}
