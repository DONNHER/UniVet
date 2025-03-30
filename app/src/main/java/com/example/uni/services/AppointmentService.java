//package com.univet.service;
//
//import com.example.uni.management.SQLiteDB;
//import com.univet.model.Appointment;
//import com.univet.database.DatabaseHelper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class AppointmentService {
//    private final DatabaseHelper dbHelper;
//    private final Map<Integer, Appointment> tempStorage = new HashMap<>();
//
//    public AppointmentService(SQLiteDB dbHelper) {
//        this.dbHelper = dbHelper;
//    }
//
//    public Appointment getAppointmentById(int appointmentId) {
//        return tempStorage.computeIfAbsent(appointmentId, dbHelper::getAppointmentById);
//    }
//
//    public void updateAppointment(Appointment appointment) {
//        tempStorage.put(appointment.getId(), appointment);
//    }
//
//    public void cancelAppointment(int appointmentId) {
//        tempStorage.remove(appointmentId);
//    }
//
//    public void saveChanges() {
//        for (Appointment appointment : tempStorage.values()) {
//            dbHelper.updateAppointment(appointment);
//        }
//        tempStorage.clear();
//    }
//}
