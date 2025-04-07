package com.example.uni.entities;

import com.example.uni.management.serviceType;

public class Appointment {

    private static String email;
    private serviceType.Services.Grooming service;
    private serviceType.Services.Medical service1;
    private serviceType.Services.Products service2;
    private serviceType.Services.Other service3;
    private String appointmentDate;
    private String appointmentTime;
    private String status;
    private final String paymentMethod;
    private double totalCost = 0;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Appointment(String email,serviceType.Services.Grooming service, String appointmentDate,String appointmentTime) {
        this.service = service;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "Pending";
        this.paymentMethod = "COD";
    }
    public Appointment(String email,serviceType.Services.Medical service, String appointmentDate, String appointmentTime) {
        this.service1 = service;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "Pending";
        this.paymentMethod = "COD";
    }
    public Appointment(String email,serviceType.Services.Products service, String appointmentDate, String appointmentTime) {
        this.service2 = service;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "Pending";
        this.paymentMethod = "COD";
    }
    public Appointment(String email,serviceType.Services.Other service, String appointmentDate, String appointmentTime) {
        this.service3 = service;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = "Pending";
        this.paymentMethod = "COD";
    }

    public serviceType.Services.Grooming getServiceType() { return service; }
    public void setServiceType(serviceType.Services.Grooming serviceType) { this.service = serviceType; }

    public serviceType.Services.Medical getServiceType1() { return service1; }
    public void setServiceType(serviceType.Services.Medical serviceType) { this.service1 = serviceType; }

    public serviceType.Services.Products getServiceType2() { return service2; }
    public void setServiceType(serviceType.Services.Products serviceType) { this.service2 = serviceType; }

    public serviceType.Services.Other getServiceType3() { return service3; }
    public void setServiceType(serviceType.Services.Other serviceType) { this.service3 = serviceType; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toString(){
        String s = "";
        s += appointmentDate +"\n";
        s += service +"\n";
        s += this.totalCost + "\n";
        s += status +"\n";
        return s;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
}
