package com.gt.express.model;


import java.util.Map;

public class Platform {
    private Map<String, Store> stores;
    private Map<String, DronePilot> pilots;
    private Map<String, Customer> customers;

    public Platform(Map<String, Store> stores, Map<String, DronePilot> pilots, Map<String, Customer> customers) {
        this.stores = stores;
        this.pilots = pilots;
        this.customers = customers;
    }

    public Map<String, Store> getStores() {
        return stores;
    }

    public void setStores(Map<String, Store> stores) {
        this.stores = stores;
    }

    public Map<String, DronePilot> getPilots() {
        return pilots;
    }

    public void setPilots(Map<String, DronePilot> pilots) {
        this.pilots = pilots;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Map<String, Customer> customers) {
        this.customers = customers;
    }
}
