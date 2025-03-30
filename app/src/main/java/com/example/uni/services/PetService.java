//package com.univet.service;
//
//import com.example.uni.management.SQLiteDB;
//import com.univet.model.Pet;
//import com.univet.database.DatabaseHelper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class PetService {
//    private final DatabaseHelper dbHelper;
//    private final Map<Integer, Pet> tempStorage = new HashMap<>();
//
//    public PetService(SQLiteDB dbHelper) {
//        this.dbHelper = dbHelper;
//    }
//
//    public Pet getPetById(int petId) {
//        return tempStorage.computeIfAbsent(petId, dbHelper::getPetById);
//    }
//
//    public void updatePet(Pet pet) {
//        tempStorage.put(pet.getId(), pet);
//    }
//
//    public void deletePet(int petId) {
//        tempStorage.remove(petId);
//    }
//
//    public void saveChanges() {
//        for (Pet pet : tempStorage.values()) {
//            dbHelper.updatePet(pet);
//        }
//        tempStorage.clear();
//    }
//}
