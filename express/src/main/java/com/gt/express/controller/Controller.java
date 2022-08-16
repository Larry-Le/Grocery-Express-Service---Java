package com.gt.express.controller;

import com.gt.express.model.Platform;
import com.gt.express.service.ExpressService;
import com.gt.express.service.util.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
//(origins = "*", allowedHeaders = "*")
@CrossOrigin
@RestController
public class Controller {
    ExpressService expressService;

    FeignService feignService;

    Platform platform;

    @Autowired
    public Controller(ExpressService expressService, FeignService feignService) {
        this.expressService = expressService;
        this.feignService = feignService;
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }


    @PostMapping(path="/store/addstore")
    public String createStore(String name, String rev) {

        String s = expressService.makeStore(name,rev);

        return s;
    }

    @GetMapping(value = "/stores",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllStore() {
        return expressService.displayStore();
    }

    @PostMapping(path="/item/additem")
    public String createItem(String storeName, String itemName, String weight) {
        return expressService.addItem(storeName,itemName,Integer.parseInt(weight));

    }

    @GetMapping(value = "/items",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllItemByStore(String storeName) {
        return expressService.displayItem(storeName);
    }

    @PostMapping(path="/pilot/addpilot")
    public String createPilot(String account, String firstName, String lastName, String phoneNumber, String taxId, String licenseId, String deliveries) {
        return expressService.createPilot(account, firstName, lastName, phoneNumber, taxId, licenseId, Integer.parseInt(deliveries));
    }

    @GetMapping(value = "/pilots",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllPilots() {
        return expressService.displayPilot();
    }

    @PostMapping(path="/drone/adddrone")
    public String createDrone(String storeName, String droneId, String capacity, String tripLeft) {
        return expressService.createDrone(storeName, droneId, Integer.parseInt(capacity), Integer.parseInt(tripLeft));
    }

    @GetMapping(value = "/drones",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllDroneByStore(String storeName) {
        return expressService.displayDrone(storeName);
    }

    @PostMapping(path="/fly_drone/assignpilot")
    public String assignPilot(String storeName, String droneId, String pilotAcc) {
        return expressService.assignPilot(storeName, droneId, pilotAcc);
    }

    @GetMapping(value = "/customers",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllCustomer() {
        return expressService.displayCustomer();
    }

    @PostMapping(path="/customers/addcustomer")
    public String addCustomer(String account, String firstName, String lastName, String phoneNumber, int rating, double credits) {
        return expressService.createCustomer(account, firstName, lastName, phoneNumber, rating, credits);
    }

    @GetMapping(value = "/orders",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllOrderByStore(String storeName) {
        return expressService.displayOrders(storeName);
    }

    @PostMapping(path="/order/addorder")
    public String createOrder(String storeName, String orderId, String droneId, String customerId) {
        return expressService.startOrder(storeName, orderId, droneId, customerId);
    }

    @PutMapping(path="/order/request")
    public String requestItem(String storeName, String orderId, String itemName, String quantity, String unitP) {
        return expressService.requestItem(storeName, orderId, itemName, Integer.parseInt(quantity),Double.parseDouble(unitP));
    }

    @PutMapping(value = "/order/purchase",produces = MediaType.APPLICATION_JSON_VALUE)
    public String purchaseOrder(String storeName, String orderId) {
        return expressService.purchaseOrder(storeName, orderId);
    }

    @PutMapping(path="order/cancelOrder")
    public String cancelItem(String storeName, String orderId) {
        return expressService.cancelOrder(storeName, orderId);
    }

    @PostMapping(value = "/stop",produces = MediaType.APPLICATION_JSON_VALUE)
    public String stopService(@RequestParam String str) {
        if (str.equals("stop")) return "stop acknowledged";
        return "Please enter 'stop' to exist";
    }

    @GetMapping(value = "/orders/userid",produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllOrderByUserID(String userId) {
        return expressService.displayOrderByCostomerId(userId);
    }




}

