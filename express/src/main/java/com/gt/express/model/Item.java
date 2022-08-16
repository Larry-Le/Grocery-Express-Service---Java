package com.gt.express.model;

public class Item {
    private Double itemPrice;
    private String itemName;
    private int weight;
    private Store store;

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item(String itemName, int weight) {
        this.itemName = itemName;
        this.weight = weight;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
