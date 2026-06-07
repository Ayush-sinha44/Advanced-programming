package com.ecommerce.payment;

import com.ecommerce.model.Order;

public class WalletPaymentProcessor implements PaymentProcessor {

    private double walletBalance;

    public WalletPaymentProcessor(double walletBalance) {
        this.walletBalance = walletBalance;
    }

    @Override
    public boolean process(Order order) {
        System.out.println("-----------------------------");
        System.out.println("[Wallet] Processing payment...");
        System.out.printf("[Wallet] Order ID        : %s%n", order.getOrderId());
        System.out.printf("[Wallet] Customer        : %s%n", order.getCustomerId());
        System.out.printf("[Wallet] Order Amount    : ₹%.2f%n", order.getAmount());
        System.out.printf("[Wallet] Wallet Balance  : ₹%.2f%n", walletBalance);

        if (walletBalance >= order.getAmount()) {
            walletBalance -= order.getAmount();
            System.out.printf("[Wallet] Remaining Balance : ₹%.2f%n", walletBalance);
            System.out.println("[Wallet] Status    : SUCCESS");
            System.out.println("-----------------------------");
            return true;
        } else {
            System.out.println("[Wallet] Status    : FAILED (Insufficient Balance)");
            System.out.println("-----------------------------");
            return false;
        }
    }
}
