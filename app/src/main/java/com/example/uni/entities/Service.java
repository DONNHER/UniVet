package com.example.uni.entities;

public class Service {
    private int id;
    private String name;
    private String description;
    private double price;
    private int estimatedDuration; // Duration in minutes
    private String serviceType; // Example: "Grooming", "Veterinary", "Transportation"

    // Constructor
    public Service(int id, String name, String description, double price, int estimatedDuration, String serviceType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.estimatedDuration = estimatedDuration;
        this.serviceType = serviceType;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", estimatedDuration=" + estimatedDuration +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}
