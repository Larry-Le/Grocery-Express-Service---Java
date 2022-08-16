package com.express.database;

import com.express.database.model.Customer;
import com.express.database.model.DronePilot;
import com.express.database.model.Store;

import java.util.Map;
import java.util.TreeMap;

@org.springframework.stereotype.Service
public class Service {
    Map<String, Store> stores = new TreeMap<>();
    Map<String, DronePilot> pilots = new TreeMap<>();
    Map<String, Customer> customers = new TreeMap<>();
}
