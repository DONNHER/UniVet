package com.example.uni.entities;

import com.example.uni.management.serviceType;

public class Appointment {
    private  String email;
    private String appointmentDate;
    private String appointmentTime;
    private String status = "Pending";
    private  String paymentMethod = "COD";
    private double totalCost = 0;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Appointment(){}
    public Appointment(String Email, String appointmentDate,String appointmentTime) {
        email = Email;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
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

}
