package com.example.uni.entities;

import com.example.uni.serviceType;

public class Appointment {
    private int id;
    private int userId;
    private int petId;
    private serviceType service;
    private String appointmentDate;
    private String status;
    private final String paymentMethod;
    private double totalCost;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Appointment(String email, serviceType service, String appointmentDate, double totalCost) {
        this.service = service;
        this.appointmentDate = appointmentDate;
        this.status = "Pending";
        this.paymentMethod = "COD";
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public serviceType getServiceType() { return service; }
    public void setServiceType(serviceType serviceType) { this.service = serviceType; }

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
}
