package com.example.uni.entities;

public class Service {
    private int id;
    private static int Image;
    private static double price;
    private String  serviceType;
    private static String name;

    private static String isNew = "new";

    // Constructor
    public Service(double Price,int image,String Name) {
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
