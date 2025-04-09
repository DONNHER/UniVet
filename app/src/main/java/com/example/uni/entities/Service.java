package com.example.uni.entities;

import java.util.UUID;

public class Service {

    private final UUID id = UUID.randomUUID();
    private static String Image;
    private static double price;
    private String  serviceType;
    private static String name;

    private static String isNew = "new";

    // Constructor
    public Service(double Price,String image,String Name) {
        Image = image;
        price = Price;
        name = Name;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Service.name = name;
    }

    public static String getIsNew() {
        return isNew;
    }

    public static void setIsNew(String New) {
        isNew = New;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public static double getPrice() {
        return price;
    }

    public static void setPrice(double Price) {
        price = Price;
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

    public static String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
