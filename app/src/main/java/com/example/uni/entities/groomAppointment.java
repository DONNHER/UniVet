package com.example.uni.entities;

import com.example.uni.management.serviceType;

public class groomAppointment extends  Appointment{
    private  String type = "Grooming";

    public groomAppointment(){}
    public groomAppointment(String Email, String appointmentDate, String appointmentTime) {
        super(Email, appointmentDate, appointmentTime);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
