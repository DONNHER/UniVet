package com.univet.service;

import com.univet.model.Service;
import com.univet.database.DatabaseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceService {
    private final DatabaseHelper dbHelper;
    private final Map<Integer, Service> tempStorage = new HashMap<>();

    public ServiceService(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Service getServiceById(int serviceId) {
        return tempStorage.computeIfAbsent(serviceId, dbHelper::getServiceById);
    }

    public void updateService(Service service) {
        tempStorage.put(service.getId(), service);
    }

    public void deleteService(int serviceId) {
        tempStorage.remove(serviceId);
    }

    public void saveChanges() {
        for (Service service : tempStorage.values()) {
            dbHelper.updateService(service);
        }
        tempStorage.clear();
    }
}
