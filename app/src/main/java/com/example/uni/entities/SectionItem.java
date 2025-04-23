package com.example.uni.entities;

public class SectionItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    public int type;
    public String date;
    public Appointment appointment;

    public SectionItem(String date) {
        this.type = TYPE_HEADER;
        this.date = date;
    }

    public SectionItem(Appointment appointment) {
        this.type = TYPE_ITEM;
        this.appointment = appointment;
    }
}
