//package com.univet.service;
//
//import com.example.uni.management.SQLiteDB;
//import com.univet.model.Owner;
//import com.univet.database.DatabaseHelper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class OwnerService {
//    private final DatabaseHelper dbHelper;
//    private final Map<Integer, Owner> tempStorage = new HashMap<>();
//
//    public OwnerService(SQLiteDB dbHelper) {
//        this.dbHelper = dbHelper;
//    }
//
//    public Owner getOwnerById(int ownerId) {
//        return tempStorage.computeIfAbsent(ownerId, dbHelper::getOwnerById);
//    }
//
//    public void updateOwner(Owner owner) {
//        tempStorage.put(owner.getId(), owner);
//    }
//
//    public void deleteOwner(int ownerId) {
//        tempStorage.remove(ownerId);
//    }
//
//    public void saveChanges() {
//        for (Owner owner : tempStorage.values()) {
//            dbHelper.updateOwner(owner);
//        }
//        tempStorage.clear();
//    }
//}
