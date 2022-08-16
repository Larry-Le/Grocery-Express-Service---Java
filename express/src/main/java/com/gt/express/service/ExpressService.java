package com.gt.express.service;

import com.gt.express.model.*;
import com.gt.express.service.util.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class ExpressService {
    Platform platform;

    @Autowired
    FeignService feignService;

    @Autowired
    UserService userService;

    public String makeStore(String name, String rev) {
        platform = feignService.getDatabase().getBody();
        if (platform.getStores().containsKey(name)) {
            return "ERROR:store_identifier_already_exists";
        } else {
            Store store1 = new Store(name, Double.parseDouble(rev));
            platform.getStores().put(name,store1);
            feignService.updatePlatform(platform);
            return "OK:change_completed";
        }

    }

    public String displayStore() {
        platform = feignService.getDatabase().getBody();
        StringBuilder sb = new StringBuilder();
        platform.getStores().forEach((k,v) -> sb.append(v.display()));
        sb.append("OK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }

    public String addItem(String storeName, String itemName, int weight) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if (platform.getStores().get(storeName).getItems().containsKey(itemName)){
            return "ERROR:item_identifier_already_exists";
        }
        platform.getStores().get(storeName).addItem(itemName, weight);
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }

    public String displayItem(String storeName) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(platform.getStores().get(storeName).displayItem());
        sb.append("OK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }

    public String createPilot(String account, String firstName, String lastName, String phoneNumber, String taxId, String licenseId, int deliveries){
        platform = feignService.getDatabase().getBody();
        if (platform.getPilots().containsKey(account)) {
            return "ERROR:pilot_identifier_already_exists";
        }

        for (Map.Entry<String, DronePilot> entry : platform.getPilots().entrySet()) {
            if (entry.getValue().getLicenseId().equals(licenseId)) {
                return "ERROR:pilot_license_already_exists";
            }
        }

        DronePilot pilot = new DronePilot(account, firstName, lastName, phoneNumber, taxId, licenseId, (int) deliveries);
        platform.getPilots().put(pilot.getAccount(), pilot);
        feignService.updatePlatform(platform);
        return "OK:change_completed";
    }

    public String displayPilot() {
        platform = feignService.getDatabase().getBody();
        StringBuilder sb = new StringBuilder();
        platform.getPilots().forEach((k,v) -> sb.append("name:"+v.getFullName()+",phone:"+v.getPhoneNumber()+",taxID:"+v.getTaxId()+",licenseID:"+v.getLicenseId()+",experience:"+v.getDeliveries()+"\n"));
        sb.append("OK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }
    // TODO addDrone
    public String createDrone(String storeName, String droneId, int capacity, int tripLeft) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if (platform.getStores().get(storeName).getDrones().containsKey(droneId)){
            return "ERROR:drone_identifier_already_exists";
        }
        //Drone drone = new Drone(droneId, capacity, tripLeft);
        platform.getStores().get(storeName).addDrone(droneId, capacity, tripLeft);
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }
    // TODO display_drone
    public String displayDrone(String storeName) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(platform.getStores().get(storeName).displayDrone());
        sb.append("OK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }
    // TODO assignPilot
    public String assignPilot(String storeName, String droneId, String pilotAcc) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if (!platform.getStores().get(storeName).getDrones().containsKey(droneId)){
            return "ERROR:drone_identifier_does_not_exists";
        } else if (!platform.getPilots().containsKey(pilotAcc)) {
            return "ERROR:pilot_identifier_does_not_exists";
        }
        Map<String, Drone> drones = platform.getStores().get(storeName).getDrones();
        for (Map.Entry<String, Drone> entry : drones.entrySet()) {
            if (entry.getValue().getPilotAcc().equals(pilotAcc)) {
                String droneID = entry.getKey();
                platform.getStores().get(storeName).getDrones().get(droneID).setPilotAcc("");
                platform.getStores().get(storeName).getDrones().get(droneID).setPilotFullname("");
            }
        }
        String fullName = platform.getPilots().get(pilotAcc).getFullName();
        platform.getStores().get(storeName).assignPilot(droneId, fullName, pilotAcc);
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }

    public String createCustomer(String account, String firstName, String lastName, String phoneNumber, int rating, double credits){
        platform = feignService.getDatabase().getBody();
        Customer customer = new Customer(account, firstName, lastName, phoneNumber, rating, credits);
        if (platform.getCustomers().containsKey(account)){
            return "ERROR:customer_identifier_already_exists";
        }
        customer.setRequestingCredits(credits);
        platform.getCustomers().put(account,customer);
        feignService.updatePlatform(platform);
        return("OK:change_completed");
    }

    public String displayCustomer() {
        platform = feignService.getDatabase().getBody();
        StringBuilder sb = new StringBuilder();
        platform.getCustomers().forEach((k,v) -> sb.append(v.display()+"\n"));
        sb.append("OK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }

    public String startOrder(String storeName, String orderId, String droneId, String customerId) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if(platform.getStores().get(storeName).getOrders().containsKey(orderId)){
            return "ERROR:order_identifier_already_exist";
        }else if (!platform.getStores().get(storeName).getDrones().containsKey(droneId)){
            return "ERROR:drone_identifier_does_not_exist";
        } else if (!platform.getCustomers().containsKey(customerId)) {
            return "ERROR:customer_identifier_does_not_exist";
        }
        platform.getStores().get(storeName).startOrder(orderId,droneId,customerId);
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }

    public String displayOrders(String storeName) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(platform.getStores().get(storeName).displayOrders());
        sb.append("\nOK:display_completed");
        feignService.updatePlatform(platform);
        return sb.toString();
    }

    public String requestItem(String storeName, String orderId, String itemName, int quantity, double unitP) {
        platform = feignService.getDatabase().getBody();
        double amount = quantity * unitP;
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if(!platform.getStores().get(storeName).getOrders().containsKey(orderId)){
            return "ERROR:order_identifier_does_not_exists";
        }else if (!platform.getStores().get(storeName).getItems().containsKey(itemName)){
            return "ERROR:item_identifier_does_not_exist";
        }else if (platform.getStores().get(storeName).getOrders().get(orderId).getItems().containsKey(itemName)){
            return "ERROR:item_already_ordered";
        }
        String customerId = platform.getStores().get(storeName).getOrders().get(orderId).getcustomerId();
        if (platform.getCustomers().get(customerId).getRequestingCredits() < amount) {
            return "ERROR:customer_cant_afford_new_item";
        }
        String droneId = platform.getStores().get(storeName).getOrders().get(orderId).getDroneId();
        int totalWeight = quantity * platform.getStores().get(storeName).getItems().get(itemName).getWeight();
        if (platform.getStores().get(storeName).getDrones().get(droneId).getCapacityLeft() < totalWeight){
            return "ERROR:drone_cant_carry_new_item";
        }
        double new_credit = platform.getCustomers().get(customerId).getcredits()-amount;
        platform.getCustomers().get(customerId).setRequestingCredits(new_credit);

        int newCapacityLeft = platform.getStores().get(storeName).getDrones().get(droneId).getCapacityLeft() - totalWeight;
        platform.getStores().get(storeName).getDrones().get(droneId).setCapacityLeft(newCapacityLeft);
        platform.getStores().get(storeName).requestItem(orderId,itemName,quantity,unitP);
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }

    public String purchaseOrder(String storeName, String orderId) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        }else if(!platform.getStores().get(storeName).getOrders().containsKey(orderId)){
            return "ERROR:order_identifier_does_not_exist";
        }
        String droneID = platform.getStores().get(storeName).getOrders().get(orderId).getDroneId();
        int tripRemainder = platform.getStores().get(storeName).getDrones().get(droneID).getTripsRemainder();
        if( tripRemainder == 0) {
            return "ERROR:drone_needs_fuel";
            //error
        } else if (platform.getStores().get(storeName).getDrones().get(droneID).getPilotFullname()=="") {
            return "ERROR:drone_needs_pilot";
        }
        String customerId = platform.getStores().get(storeName).getOrders().get(orderId).getcustomerId();
        double amount = platform.getStores().get(storeName).checkPrice(orderId);
        if (platform.getCustomers().get(customerId).getcredits() < amount) {
            return "ERROR:customer_credits_arent_enough";
        }
        platform.getCustomers().get(customerId).setcredits(platform.getCustomers().get(customerId).getcredits() - amount);
        platform.getStores().get(storeName).setRevenue(platform.getStores().get(storeName).getRevenue() + amount);
        String pilotId = platform.getStores().get(storeName).purchaseOrder(orderId);
        platform.getStores().get(storeName).getDrones().get(droneID).setTripsRemainder(tripRemainder - 1);
        platform.getPilots().get(pilotId).setDeliveries(platform.getPilots().get(pilotId).getDeliveries() + 1);
        int weight = platform.getStores().get(storeName).getOrders().get(orderId).getTotalWeight();
        int curCapacity = platform.getStores().get(storeName).getDrones().get(droneID).getCapacityLeft();
        platform.getStores().get(storeName).getDrones().get(droneID).setCapacityLeft(curCapacity+weight);
        platform.getStores().get(storeName).removeOrder(orderId);
        //platform.getStores().get(storeName).getDrones().get(droneID).setPilotAcc("");
        //platform.getStores().get(storeName).getDrones().get(droneID).setPilotFullname("");
        feignService.updatePlatform(platform);
        return ("OK:change_completed");
    }


    public String cancelOrder(String storeName, String orderId) {
        platform = feignService.getDatabase().getBody();
        if (!platform.getStores().containsKey(storeName)) {
            return "ERROR:store_identifier_does_not_exist";
        } else if (!platform.getStores().get(storeName).getOrders().containsKey(orderId)) {
            return "ERROR:order_identifier_does_not_exists";
        }
        String customerId = platform.getStores().get(storeName).getOrders().get(orderId).getcustomerId();
        double amount = platform.getStores().get(storeName).getOrders().get(orderId).getTotalPrice();
        double new_credit = platform.getCustomers().get(customerId).getRequestingCredits()+amount;
        platform.getCustomers().get(customerId).setRequestingCredits(new_credit);

        String droneId = platform.getStores().get(storeName).getOrders().get(orderId).getDroneId();
        int totalWeight = platform.getStores().get(storeName).getOrders().get(orderId).getTotalWeight();
        int new_capacityLeft = platform.getStores().get(storeName).getDrones().get(droneId).getCapacityLeft() + totalWeight;
        platform.getStores().get(storeName).getDrones().get(droneId).setCapacityLeft(new_capacityLeft);
        platform.getStores().get(storeName).getOrders().remove(orderId);
        feignService.updatePlatform(platform);
        return "OK:change_completed";
    }

    public String displayOrderByCostomerId(String userID) {
        platform = feignService.getDatabase().getBody();
        StringBuilder sb = new StringBuilder();
        platform.getStores().forEach((k,v) -> sb.append(v.displayOrderByUserId(userID)));
        feignService.updatePlatform(platform);
        return sb.toString();
    }



}
