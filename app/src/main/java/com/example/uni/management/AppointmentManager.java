package com.example.uni.management;

import com.example.uni.entities.Appointment;
import com.example.uni.serviceType;

import java.util.ArrayList;

public class AppointmentManager {
    private ArrayList<Appointment> appointments;


    public void create(String email, serviceType service, String date, double cost) {
        Appointment newApp = new Appointment(email, service, date, cost);
        appointments.add(newApp);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
}

