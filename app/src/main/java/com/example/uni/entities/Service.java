package com.example.uni.entities;

public class Service {
    private int id;
    private double price;
    private String  serviceType;
    // Constructor
    public Service(double price, String serviceType) {
        this.price = price;
        this.serviceType = serviceType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return "Service{" +
                ", price=" + price +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}
