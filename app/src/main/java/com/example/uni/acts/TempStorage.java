package com.example.uni.acts;

import android.widget.Toast;

import com.example.uni.entities.Appointment;
import com.example.uni.entities.owner;

import java.util.ArrayList;

public class TempStorage {
    private static TempStorage temp;
    private  ArrayList<owner> users;
    private  ArrayList<Appointment> appointments;
    private TempStorage(){
        users = new ArrayList<>();
    }

    public static synchronized  TempStorage getInstance(){
        if (temp == null){
            temp = new TempStorage();
        }
        return  temp;
    }
    public ArrayList<owner> getUsers(){return users;}
    public ArrayList<Appointment>getAppointments(){return appointments;}
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
}
