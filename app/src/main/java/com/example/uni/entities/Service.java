package com.example.uni.entities;

import com.google.firebase.firestore.PropertyName;

import java.util.UUID;

public class Service {

    private final UUID id = UUID.randomUUID();
    private  String Image;
    private  double price;
    private String  serviceType;
    private  String name;
    private  String isNew = "new";
    private String description;

    // Constructor
    public Service(){}
    public Service(double Price,String image,String Name,String description) {
        Image = image;
        price = Price;
        name = Name;
        this.description = description;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  String getIsNew() {
        return isNew;
    }

    public  void setIsNew(String New) {
        isNew = New;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public  double getPrice() {
        return price;
    }

    public  void setPrice(double Price) {
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

    public  String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    @PropertyName("Description")
    public String getDescription() {
        return description;
    }
    @PropertyName("Description")
    public void setDescription(String description) {
        this.description = description;
    }
}
