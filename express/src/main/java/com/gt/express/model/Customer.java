package com.gt.express.model;


import java.util.List;

public class Customer extends User{
    private String account;
    private List<Order> orderList;
    private String customerAddress;
    private double credits;
    private double requestingCredits;
    private int rating;
    private double totalCost;
    private boolean paymentPending;

    public Customer(String account, String firstName, String lastName, String phoneNumber, int rating, double credits) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.credits = credits;
    }
    
    public String getAccount() {
        return account;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public double getcredits() {
        return credits;
    }

    public void setcredits(double credits) {
        this.credits = credits;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isPaymentPending() {
        return paymentPending;
    }

    public void setPaymentPending(boolean paymentPending) {
        this.paymentPending = paymentPending;
    }

    public double getRequestingCredits() {
        return requestingCredits;
    }

    public void setRequestingCredits(double requestingCredits) {
        this.requestingCredits = requestingCredits;
    }

    public String display() {
        return "name:" + this.firstName + "_" + this.lastName
                + ",phone:" + this.phoneNumber + ",rating:" + this.rating + ",credit:" + (int)this.credits;
    }

}
