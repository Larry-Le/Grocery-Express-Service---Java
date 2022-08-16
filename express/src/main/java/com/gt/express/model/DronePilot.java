package com.gt.express.model;

import java.time.LocalDate;

public class DronePilot extends User {
    private String account;
    private String taxId;
    private double salary;
    private int storeId;
    private String licenseId;
    private int deliveries;
    private LocalDate startDate;
    private int droneId;

    public DronePilot(String account, String firstName, String lastName, String phoneNumber, String taxId, String licenseId, int deliveries) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.taxId = taxId;
        this.licenseId = licenseId;
        this.deliveries = deliveries;
    }

    public String getAccount() {
        return account;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public int getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(int deliveries) {
        this.deliveries = deliveries;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDroneId() {
        return droneId;
    }

    public void setDroneId(int droneId) {
        this.droneId = droneId;
    }

    public String getFullName() {
        return firstName + "_" + lastName;
    }
}
