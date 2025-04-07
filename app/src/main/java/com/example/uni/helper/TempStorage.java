package com.example.uni.helper;

import com.example.uni.entities.Appointment;
import com.example.uni.entities.Service;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class TempStorage {
    private  ArrayList<owner> users;
    private  ArrayList<Appointment> appointments;
    private  ArrayList<Appointment> pAppointments;
    private  ArrayList<Service> services;
    private TempStorage(){
        users = new ArrayList<>();
        appointments = new ArrayList<>();
        services = new ArrayList<>();
        pAppointments = new ArrayList<>();
    }

    private static class TempStorageHelper {
        private static final TempStorage instance = new TempStorage();
    }
    public static TempStorage getInstance(){
        return TempStorageHelper.instance;
    }
    public ArrayList<owner> getUsers(){return users;}
    public ArrayList<Appointment>getAppointments(){return appointments;}
    public ArrayList<Appointment>getPAppointments(){return appointments;}
    public owner getUser(owner user, ArrayList<owner> users){
        int l = 0;
        int r = users.size() -1;
        while (l <= r){
            int m = l + (r - l) / 2;
                int find = users.get(m).getEmail().compareTo(user.getEmail());
                if (find == 0){
                    return users.get(m);
                } else if (find < 0){
                    l = m + 1;
                }else {
                    r = m - 1;
                }
            }
        return null;
    }

    protected static int findIndexInsertion(owner o, ArrayList<owner> users) {
        int left = 0, right = users.size();
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (users.get(mid).getEmail().compareTo(o.getEmail()) >= 0) {
                right = mid;  // Search in the left half
            } else {
                left = mid + 1;  // Search in the right half
            }
        }
        return left; // Returns the correct index for insertion
    }
    public boolean addUser(owner user){
        if(getUser(user,this.users) == null) {
            this.users.add(findIndexInsertion(user, this.users), user);
            return true;
        }
        return false;
    }
    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }
    public void addAppointment(Service service){
        services.add(service);
    }
    public void addPAppointment(Appointment appointment){
        pAppointments.add(appointment);
    }

}
