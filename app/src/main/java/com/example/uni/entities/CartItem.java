package com.example.uni.entities;

import com.google.firebase.firestore.PropertyName;
import java.util.Date;

public class CartItem {
    private String id;
    private String itemId;
    private String itemName;
    private String itemImage;
    private double itemPrice;
    private int quantity;
    private String userId;
    private Date addedDate;
    private String serviceType;

    public CartItem() {
        // Required empty constructor for Firestore
    }

    public CartItem(String itemId, String itemName, String itemImage, double itemPrice, 
                   int quantity, String userId, String serviceType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.userId = userId;
        this.serviceType = serviceType;
        this.addedDate = new Date();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getTotalPrice() {
        return itemPrice * quantity;
    }
}