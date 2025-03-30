package com.example.uni.entities;
public class Appointment {
    private int id;
    private int userId;
    private int petId;
    private String serviceType;
    private String appointmentDate;
    private String status;
    private final String paymentMethod;
    private double totalCost;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Appointment(int userId, int petId, String serviceType, String appointmentDate, double totalCost) {
        this.userId = userId;
        this.petId = petId;
        this.serviceType = serviceType;
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

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

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
