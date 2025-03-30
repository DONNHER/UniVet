package com.example.uni.entities;

public class pet {
    private int id;
    private String name;
    private int ownerId; // Reference to the user (pet owner)
    private String species;
    private String breed;
    private int age;
    private String medicalHistory;

    // Constructor
    public pet(int id, String name, int ownerId, String species, String breed, int age, String medicalHistory) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.medicalHistory = medicalHistory;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
