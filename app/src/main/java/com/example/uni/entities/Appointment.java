package com.example.uni.entities;

import java.util.UUID;

public class Appointment {
    private  String email;
    private String appointmentDate;
    private String appointmentTime;
    private String status = "Pending";;
    private  String paymentMethod = "COD";
    private double totalCost = 0;
    private String createdAt;
    private String updatedAt;
    private String id ;
    private String userID;
    private String serviceID;
    private String patientName;
    private String price;
    private String services;


    // Constructor
    public Appointment(){}
    public Appointment(String Email, String appointmentDate,String appointmentTime,String name,String price,String services) {
        email = Email;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientName =name;
        this.price = price;
        this.services = services;

    }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return this.paymentMethod; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    public String getId() {
        return id;
    }
    public void setId(String  id){
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
