package com.example.uni.fragments;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Appointment_manage extends AppCompatActivity {
    private String date, time, name, services, appointmentId, status , userID;
    private String totalCost;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_manage);

        if (getIntent() != null) {
            appointmentId = getIntent().getStringExtra("appointmentId");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            totalCost = getIntent().getStringExtra("price");
            name = getIntent().getStringExtra("name");
            services = getIntent().getStringExtra("services");
            status = getIntent().getStringExtra("status");
            userID = getIntent().getStringExtra("userID");

        }

        TextView Date = findViewById(R.id.Date);
        TextView Time = findViewById(R.id.Time);
        TextView tCost = findViewById(R.id.cost);
        TextView service = findViewById(R.id.service);

        Date.setText(date);
        Time.setText(time);
        tCost.setText(totalCost);
        service.setText(services);

        findViewById(R.id.confirm).setOnClickListener(v -> updateStatus("Confirmed"));
        findViewById(R.id.cancel).setOnClickListener(v -> updateStatus("Cancelled"));
    }

    private void updateStatus(String status) {

        db.collection("Appointments").document(appointmentId)
                .update("status", status)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Appointment " + status, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
        db.collection("users").document("user").collection("account").document(userID).collection("appointments")
                .document(appointmentId).update("status", status)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Appointment " + status, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}