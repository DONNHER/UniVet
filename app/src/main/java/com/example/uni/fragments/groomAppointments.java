package com.example.uni.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class groomAppointments extends DialogFragment {
    private CalendarView calendarView;
    private Button scheduleButton;
    private static TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private String name;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  String id;

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
            if (time == null || time.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }

            long date = calendarView.getDate();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd ,yyyy");
            String Date = simpleDateFormat.format(date);
            String Time = time.getText().toString();

            String docId = UUID.randomUUID().toString();

            Appointment appointment = new Appointment(
                    myAuth.getCurrentUser().getDisplayName(), // name
                    Date,
                    Time
            );
            appointment.setId(docId);
            appointment.setStatus("Pending");
            appointment.setUserID(myAuth.getCurrentUser().getUid());
            appointment.setServiceID(id); // assuming you add this field to Appointment

            db.collection("Appointments").document(docId).set(appointment)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Appointment scheduled successfully!", Toast.LENGTH_SHORT).show();
                        requireActivity().recreate();
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
        return view;
    }
}
