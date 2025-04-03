package com.example.uni.entities;

import com.example.uni.serviceType;

public class Appointment {

    private String email;
    private serviceType.Services service;
    private String appointmentDate;
    private String status;
    private final String paymentMethod;
    private double totalCost = 500.0;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Appointment(String email, serviceType.Services service, String appointmentDate) {
        this.email = email;
        this.service = service;
        this.appointmentDate = appointmentDate;
        this.status = "Pending";
        this.paymentMethod = "COD";
    }

    public serviceType.Services getServiceType() { return service; }
    public void setServiceType(serviceType.Services serviceType) { this.service = serviceType; }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
