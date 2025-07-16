package com.example.uni.entities;

import java.io.Serializable;

public class Pet implements Serializable {
    
    private String petId;
    private String ownerId;
    private String name;
    private String breed;
    private String age;
    private String gender;
    private String species; // Dog, Cat, Bird, etc.
    private String color;
    private double weight;
    private String imageUrl;
    private String medicalHistory;
    private String vaccinations;
    private String notes;
    private String dateRegistered;
    private boolean isActive;
    
    // Default constructor (required for Firebase)
    public Pet() {
    }
    
    // Constructor with basic info
    public Pet(String name, String breed, String age, String ownerId) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.ownerId = ownerId;
        this.isActive = true;
    }
    
    // Full constructor
    public Pet(String petId, String ownerId, String name, String breed, String age, 
               String gender, String species, String color, double weight, String imageUrl) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.species = species;
        this.color = color;
        this.weight = weight;
        this.imageUrl = imageUrl;
        this.isActive = true;
    }
    
    // Getters and Setters
    public String getPetId() {
        return petId;
    }
    
    public void setPetId(String petId) {
        this.petId = petId;
    }
    
    public String getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public String getAge() {
        return age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public String getVaccinations() {
        return vaccinations;
    }
    
    public void setVaccinations(String vaccinations) {
        this.vaccinations = vaccinations;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getDateRegistered() {
        return dateRegistered;
    }
    
    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return "Pet{" +
                "petId='" + petId + '\'' +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", age='" + age + '\'' +
                ", species='" + species + '\'' +
                '}';
    }
}