package com.express.database.model;

import java.util.Map;
import java.util.TreeMap;

public class Order {
    private String orderId;
    private Map<String, Item> items = new TreeMap<>();
    private double totalPrice;
    private String droneId;
    private String customerId;
    private boolean deliveryPending;
    private boolean paymentPending;
    private int totalWeight;
    private String storeName;
    private int quantity;


    public Order(String orderId, String droneId, String customerId) {
        this.orderId = orderId;
        this.droneId = droneId;
        this.customerId = customerId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getcustomerId() {
        return customerId;
    }

    public void setcustomerId(String customerId) {
        customerId = customerId;
    }

    public boolean isDeliveryPending() {
        return deliveryPending;
    }

    public void setDeliveryPending(boolean deliveryPending) {
        this.deliveryPending = deliveryPending;
    }

    public boolean isPaymentPending() {
        return paymentPending;
    }

    public void setPaymentPending(boolean paymentPending) {
        this.paymentPending = paymentPending;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void requestItem(String itemName, int totalWeight, double totalPrice,int quantity) {
        Item item = new Item(itemName);
        items.put(itemName, item);
        this.totalWeight = totalWeight;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }


}
