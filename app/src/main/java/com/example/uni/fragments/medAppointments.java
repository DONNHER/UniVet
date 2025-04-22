package com.example.uni.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.groomAppointment;
import com.example.uni.entities.medAppointment;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;

public class medAppointments extends DialogFragment {
    private CalendarView calendarView;
    private Button scheduleButton;
    private static TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_act, container, false); // Ensure this matches your XML file name
//        recyclerView = view.findViewById(R.id.recycleView);
//        recyclerView2 = view.findViewById(R.id.recycleView2);
        // Initialize UI elements
        calendarView = view.findViewById(R.id.calendarView);
        scheduleButton = view.findViewById(R.id.scheduleButton);
        EditText time = view.findViewById(R.id.time);
//        TextView cost = view.findViewById(R.id.price);
        scheduleButton.setOnClickListener(v -> {
            // Handle button click to schedule appointment
            if (time == null || time.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }
            long date = calendarView.getDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd ,yyyy");
            String Date = simpleDateFormat.format(date);
            String Time = time.getText().toString();
            Appointment newAppointment = new medAppointment(myAuth.getCurrentUser().getDisplayName(), Date, Time);
            temp.addAppointment(newAppointment);
            temp.addPAppointment(newAppointment);
            Toast.makeText(getContext(), "Successful Appointment", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }

}
