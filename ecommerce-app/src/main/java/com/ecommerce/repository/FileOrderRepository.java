package com.ecommerce.repository;

import com.ecommerce.model.Order;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileOrderRepository implements OrderRepository {

    private final String storageDirectory;

    public FileOrderRepository(String storageDirectory) {
        this.storageDirectory = storageDirectory;
        createDirectoryIfNotExists();
    }

    // ── Creates the storage folder if it doesn't already exist ──
    private void createDirectoryIfNotExists() {
        File dir = new File(storageDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.printf("[FileRepo] Created storage directory: %s%n", storageDirectory);
        }
    }

    // ── Each order is saved as its own .txt file ──
    private String buildFilePath(String orderId) {
        return storageDirectory + File.separator + orderId + ".txt";
    }

    // ── Serializes an Order into a readable text format ──
    private String serialize(Order order) {
        return "orderId="    + order.getOrderId()    + "\n" +
                "customerId=" + order.getCustomerId() + "\n" +
                "type="       + order.getType()       + "\n" +
                "amount="     + order.getAmount()     + "\n" +
                "status="     + order.getStatus();
    }

    // ── Reads a .txt file back into an Order object ──
    private Order deserialize(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));

        String orderId    = null;
        String customerId = null;
        String type       = null;
        double amount     = 0.0;
        String status     = null;

        for (String line : lines) {
            String[] parts = line.split("=", 2);
            if (parts.length < 2) continue;
            switch (parts[0].trim()) {
                case "orderId"    -> orderId    = parts[1].trim();
                case "customerId" -> customerId = parts[1].trim();
                case "type"       -> type       = parts[1].trim();
                case "amount"     -> amount     = Double.parseDouble(parts[1].trim());
                case "status"     -> status     = parts[1].trim();
            }
        }

        Order order = new Order(orderId, customerId, amount, type);
        order.setStatus(status);
        return order;
    }

    @Override
    public void save(Order order) {
        String filePath = buildFilePath(order.getOrderId());
        try {
            Files.writeString(Path.of(filePath), serialize(order));
            System.out.println("-----------------------------");
            System.out.println("[FileRepo] Order saved successfully.");
            System.out.printf("[FileRepo] File     : %s%n", filePath);
            System.out.printf("[FileRepo] Order ID : %s%n", order.getOrderId());
            System.out.printf("[FileRepo] Customer : %s%n", order.getCustomerId());
            System.out.printf("[FileRepo] Type     : %s%n", order.getType());
            System.out.printf("[FileRepo] Amount   : ₹%.2f%n", order.getAmount());
            System.out.printf("[FileRepo] Status   : %s%n", order.getStatus());
            System.out.println("-----------------------------");
        } catch (IOException e) {
            System.out.printf("[FileRepo] ERROR saving order %s: %s%n",
                    order.getOrderId(), e.getMessage());
            throw new RuntimeException("Failed to save order to file.", e);
        }
    }

    @Override
    public Order findById(String orderId) {
        String filePath = buildFilePath(orderId);
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.printf("[FileRepo] Order not found: %s%n", orderId);
            return null;
        }

        try {
            Order order = deserialize(filePath);
            System.out.printf("[FileRepo] Order loaded: %s%n", orderId);
            return order;
        } catch (IOException e) {
            System.out.printf("[FileRepo] ERROR reading order %s: %s%n",
                    orderId, e.getMessage());
            throw new RuntimeException("Failed to read order from file.", e);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        File dir = new File(storageDirectory);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("[FileRepo] No orders found in storage.");
            return orders;
        }

        for (File file : files) {
            try {
                orders.add(deserialize(file.getAbsolutePath()));
            } catch (IOException e) {
                System.out.printf("[FileRepo] Skipping unreadable file: %s%n", file.getName());
            }
        }

        System.out.printf("[FileRepo] Loaded %d order(s) from storage.%n", orders.size());
        return orders;
    }

    @Override
    public void deleteById(String orderId) {
        File file = new File(buildFilePath(orderId));

        if (!file.exists()) {
            System.out.printf("[FileRepo] Cannot delete — file not found: %s%n", orderId);
            return;
        }

        if (file.delete()) {
            System.out.printf("[FileRepo] Order deleted: %s%n", orderId);
        } else {
            System.out.printf("[FileRepo] ERROR: Could not delete file for order: %s%n", orderId);
        }
    }
}