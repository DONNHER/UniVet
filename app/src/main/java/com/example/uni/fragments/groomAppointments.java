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
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class groomAppointments extends DialogFragment {
    private CalendarView calendarView;
    private Button scheduleButton;
    private static TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private String id;
    private String name;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public groomAppointments(String id, String name){
        this.id = id;
        this.name = name;
    }
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
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd ,yyyy");
            String Date = simpleDateFormat.format(date);
            String Time = time.getText().toString();

            Map<String, Object> data = new HashMap<>();
            data.put("uid", id);
            data.put("name", name);
            data.put("Date", Date);
            data.put("time", Time);

            db.collection("Appointments").add(data).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getContext(), "Successful " + name, Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            });
        return view;
    }
}
