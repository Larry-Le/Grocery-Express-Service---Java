package com.express.database.model;

import java.util.Map;
import java.util.TreeMap;

public class Store {
    private Map<String, Item> items = new TreeMap<>();
    private String storeName;
    private String storeId;
    private String storeAddress;
    private String zipcode;
    private double revenue;
    private Map<String, DronePilot> pilots = new TreeMap<>();
    private Map<String, Drone> drones = new TreeMap<>();
    private Map<String, Order> orders = new TreeMap<>();

    public Store(String storeName, double revenue) {
        this.storeName = storeName;
        this.revenue = revenue;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }
    public Map<String, Drone> getDrones() {return drones; }

    public Map<String, Item> getItems() {
        return items;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public Map<String, DronePilot> getPilots() {
        return pilots;
    }

    public void addDrone(String droneId, int maxCapacity, int tripLeft) {
        Drone drone = new Drone(this.storeName, droneId, maxCapacity, tripLeft);
        drones.put(droneId, drone);
    }

    public void addItem(String itemName, int weight) {
        Item item = new Item(itemName, weight);
        items.put(itemName, item);
    }

    public String displayDrone() {
        StringBuilder sb = new StringBuilder();
        drones.forEach((k,v) -> sb.append(v.display()+"\n"));
        return sb.toString();
    }

    public String display() {
//        System.out.println("name:" + storeName + ",revenue:" + (int)revenue);
        return ("name:" + storeName + ",revenue:" + (int)revenue+"\n");
    }

    public String displayItem() {
        StringBuilder sb = new StringBuilder();
        items.forEach((k,v) -> sb.append(v.getItemName()+","+v.getWeight()+"\n"));
        return sb.toString();
    }

    public void assignPilot(String droneId, String fullName, String pilotAcc) {
        drones.get(droneId).setPilotFullname(fullName);
        drones.get(droneId).setPilotAcc(pilotAcc);
    }

    public void startOrder(String orderId, String droneId, String customerId) {
        Order order = new Order(orderId,droneId,customerId);
        this.drones.get(droneId).getOrders().put(orderId,order);
        orders.put(orderId, order);
    }

    public String displayOrders() {
        StringBuilder sb = new StringBuilder();
        Map<String, Order> order = this.orders;
        for (Map.Entry<String, Order> entry : orders.entrySet()) {
            sb.append("\n"+"orderID:" + entry.getValue().getOrderId());
            if (!entry.getValue().getItems().isEmpty()) {
                for (Map.Entry<String, Item> entryB  : entry.getValue().getItems().entrySet()) {
                    sb.append("\nitem_name:" + entryB.getKey() +",total_quantity:" + entry.getValue().getQuantity() + ",total_cost:" +
                            (int)entry.getValue().getTotalPrice() + ",total_weight:" + entry.getValue().getTotalWeight());
                }

            }
        }
/*        orders.forEach((k,v) ->
        {
            sb.append("\n"+"orderID:" + v.getOrderId());
            if (v.getItems().size() > 0) {
                v.getItems().forEach((a, b) -> {
                    sb.append("\nitem_name:" + a+",total_quantity:" + v.getQuantity() + ",total_cost:" +
                        (int)v.getTotalPrice() + ",total_weight:" + v.getTotalWeight());
                }
                );
            }
        });*/
        sb.delete(0,1);
        return sb.toString();
    }

    public void requestItem(String orderId, String itemName, int quantity, double unitP) {
        int totalWeight = items.get(itemName).getWeight() * quantity;
        double totalP = unitP * quantity;
/*        if (drones.get(orders.get(orderId).getDroneId()).getCapacityLeft() < totalWeight) {
            return;
        }*/
        orders.get(orderId).requestItem(itemName,totalWeight,totalP,quantity);
    }

    public double checkPrice(String orderId) {
        Order order = orders.get(orderId);
     //   String droneId = orders.get(orderId).getDroneId();
//        if (drones.get(droneId).getTripsRemainder() <= 0 || drones.get(droneId).getPilotFullname().isEmpty()){
//            return Double.MAX_VALUE;
//        }
        return order.getTotalPrice();
    }

    public String purchaseOrder(String orderId) {
        String droneId = orders.get(orderId).getDroneId();
        drones.get(droneId).setTripsRemainder(drones.get(droneId).getTripsRemainder() - 1);
        return drones.get(droneId).getPilotAcc();
    }

    public void removeOrder(String orderId) {
        String droneId = orders.get(orderId).getDroneId();
        drones.get(droneId).getOrders().remove(orderId);
        orders.remove(orderId);
    }

    public String displayOrderByUserId(String userId) {
        StringBuilder sb = new StringBuilder();
        Map<String, Order> order = this.orders;
        for (Map.Entry<String, Order> entry : orders.entrySet()) {
            if(entry.getValue().getcustomerId().equals(userId)) {
                sb.append("\n" + "orderID:" + entry.getValue().getOrderId());
                if (!entry.getValue().getItems().isEmpty()) {
                    for (Map.Entry<String, Item> entryB : entry.getValue().getItems().entrySet()) {
                        sb.append("\nitem_name:" + entryB.getKey() + ",total_quantity:" + entry.getValue().getQuantity() + ",total_cost:" +
                                (int) entry.getValue().getTotalPrice() + ",total_weight:" + entry.getValue().getTotalWeight());
                    }

                }
            }
        }
        if (sb.length() > 0) {
            sb.delete(0,1);
        }
        return sb.toString();
    }
}
