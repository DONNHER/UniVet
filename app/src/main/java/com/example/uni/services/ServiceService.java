package com.example.uni.services;

import com.example.uni.entities.Service;
import com.example.uni.management.SQLiteDB;

import java.util.HashMap;
import java.util.Map;

public class ServiceService {
    private final SQLiteDB dbHelper;
    private final Map<Integer, Service> tempStorage = new HashMap<>();

    public ServiceService(SQLiteDB dbHelper) {
        this.dbHelper = dbHelper;
    }

//    public Service getServiceById(int serviceId) {
//        return tempStorage.computeIfAbsent(serviceId, dbHelper::getServiceById);
//    }

    public void deleteService(int serviceId) {
        tempStorage.remove(serviceId);
    }
//
//    public void saveChanges() {
//        for (Service service : tempStorage.values()) {
//            dbHelper.updateService(service);
//        }
//        tempStorage.clear();
//    }
}
