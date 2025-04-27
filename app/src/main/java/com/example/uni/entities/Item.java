package com.example.uni.entities;

import com.google.firebase.firestore.PropertyName;

import java.util.UUID;

public class Item {

    private  String id ;
    private  String Image;
    private  double price;
    private String  serviceType;
    private  String name;
    private int slots;


    // Constructor
    public Item(){}
    public Item(double Price,String image,String Name,int slots) {
        Image = image;
        price = Price;
        name = Name;
        this.slots = slots;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }


    // Getters and Setters
    public String getId() {
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


    public void setId(String id) {
        this.id = id;
    }

    public int getSlot() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }
}
