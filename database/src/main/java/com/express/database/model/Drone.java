package com.express.database.model;

import java.util.Map;
import java.util.TreeMap;

public class Drone {
    private String status;
    private String droneId;
    private String pilotFullname = "";
    private String storeName;
    private int tripsRemainder;
    private int maxCapacity;
    private int capacityLeft;
    private Map<String,Order> orders = new TreeMap<>();
    private String pilotAcc = "";

    public Drone(String storeName, String droneId, int maxCapacity, int tripsRemainder) {
        this.droneId = droneId;
        this.storeName = storeName;
        this.tripsRemainder = tripsRemainder;
        this.maxCapacity = maxCapacity;
        this.capacityLeft = maxCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getPilotFullname() {
        return pilotFullname;
    }

    public void setPilotFullname(String pilotFullname) {
        this.pilotFullname = pilotFullname;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getTripsRemainder() {
        return tripsRemainder;
    }

    public void setTripsRemainder(int tripsRemainder) {
        this.tripsRemainder = tripsRemainder;
    }

    public String getPilotAcc() {
        return pilotAcc;
    }

    public void setPilotAcc(String pilotAcc) {
        this.pilotAcc = pilotAcc;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCapacityLeft() {
        return capacityLeft;
    }

    public void setCapacityLeft(int capacityLeft) {
        this.capacityLeft = capacityLeft;
    }

    public Map<String,Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("droneID:" + droneId + ",total_cap:"
                + maxCapacity + ",num_orders:" + orders.size()
                + ",remaining_cap:" + capacityLeft + ",trips_left:" + tripsRemainder);
        if (!pilotFullname.equals("")) {
            sb.append(",flown_by:" + pilotFullname);
        }
        return sb.toString();
    }
}
