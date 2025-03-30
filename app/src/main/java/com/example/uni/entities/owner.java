package com.example.uni.entities;

import com.example.uni.serviceType;

import java.util.ArrayList;

public class owner {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    private String password;
    private  ArrayList<serviceType> transactions;
    // Constructor
    public owner( String name, String password) {
        this.name = name;
        this.password = password;
    }
    public owner(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
    public owner(int id, String name, String email, String phone, String address, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password =password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword(){ return  password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<serviceType> getTransactions() {
        return transactions;
    }

    private void addService(String email, serviceType.Services type, String description){
            serviceType newTransaction = new serviceType(email,type,description);
            transactions.add(newTransaction);
        }
    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
