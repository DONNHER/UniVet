package com.example.uni.entities;

import java.util.Date;
import java.util.List;

public class Order {
    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED
    }

    public enum PaymentStatus {
        CASH_ON_DELIVERY,
        PAID
    }

    private String id;
    private String userId;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private List<CartItem> items;
    private double totalAmount;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private Date orderDate;
    private Date estimatedDeliveryDate;
    private Date actualDeliveryDate;
    private String trackingNumber;
    private String notes;

    public Order() {
        // Required empty constructor for Firestore
    }

    public Order(String userId, String customerName, String customerPhone, 
                String deliveryAddress, List<CartItem> items, double totalAmount) {
        this.userId = userId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.deliveryAddress = deliveryAddress;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.CASH_ON_DELIVERY;
        this.orderDate = new Date();
        this.trackingNumber = generateTrackingNumber();
    }

    private String generateTrackingNumber() {
        return "UV" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatusDisplayText() {
        switch (status) {
            case PENDING:
                return "Order Pending";
            case CONFIRMED:
                return "Order Confirmed";
            case PROCESSING:
                return "Processing";
            case OUT_FOR_DELIVERY:
                return "Out for Delivery";
            case DELIVERED:
                return "Delivered";
            case CANCELLED:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }
}