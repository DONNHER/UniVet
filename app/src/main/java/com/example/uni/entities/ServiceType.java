package com.example.uni.entities;

import com.google.firebase.firestore.PropertyName;

import java.util.UUID;

public class ServiceType {
    private  UUID id = UUID.randomUUID();
    private String Image;
    private  String name;
    private String description;

    public ServiceType(){

    }
    // Constructor
    public ServiceType(String description, String image, String Name) {
        this.id = UUID.randomUUID();
        this.Image = image;
        this.description = description;
        this.name = Name;
    }


    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "ServiceType{name='" + name + "', description='" + description + "', image='" + Image + "'}";
    }

}
