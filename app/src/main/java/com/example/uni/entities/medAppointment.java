package com.example.uni.entities;

public class medAppointment extends  Appointment{
        private  String type = "Grooming";


    public medAppointment(){}
    public medAppointment(String Email, String appointmentDate, String appointmentTime) {
            super(Email, appointmentDate, appointmentTime);
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

