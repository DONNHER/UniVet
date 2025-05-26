package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.appAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.fragments.Menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TechnicianDashB extends AppCompatActivity {
    private RecyclerView recyclerView ;
    private appAdapt Adapt;
    private Button btn1, btn2;

    private ArrayList<Appointment> appointments = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_dash);
        btn1 = findViewById(R.id.appointments);
        btn2 = findViewById(R.id.appointments3);
        recyclerView = findViewById(R.id.appointmentsView4);
        Adapt = new appAdapt();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adapt);
        filter("Confirmed");
        btn2.setOnClickListener(v ->filter("Pending"));
        btn1.setOnClickListener(v ->filter("Confirmed"));
        services();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onMenuClick(View view) {
        Menu menu = new Menu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }
    private void services(){
        db.collection("Appointments").get().addOnSuccessListener(queryDocumentSnapshots -> {
            appointments.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Appointment appt = documentSnapshot.toObject(Appointment.class);
                    appt.setId(documentSnapshot.getId());  // ✅ Fix: Set the document ID manually
                    appointments.add(appt);
            }
            filter("Confirmed");
        });
    }



    private void filter(String status) {
        ArrayList<Appointment> filtered = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals(status)) {
                filtered.add(appointment);
            }
        }

        // Group appointments by date
        Map<String, List<Appointment>> grouped = groupByDate(filtered);
        Adapt.setAppointments(grouped,this,getSupportFragmentManager());
    }

    private Map<String, List<Appointment>> groupByDate(List<Appointment> appointments) {
        Map<String, List<Appointment>> grouped = new LinkedHashMap<>();
        for (Appointment appointment : appointments) {
            String date = appointment.getAppointmentDate(); // Assuming getDate() returns the date as a string
            if (!grouped.containsKey(date)) {
                grouped.put(date, new ArrayList<>());
            }
            grouped.get(date).add(appointment);
        }
        return grouped;
    }

    public void onManageClick(View view) {
        Intent intent = new Intent(this, ManageServiceType.class); // Replace with actual target
        startActivity(intent);
    }
    @Override
    public void onResume(){
       super.onResume();
       services();
    }
    public void onInventoryClick(View view){
        Intent intent = new Intent(this, manager_inventory.class); // Replace with actual target
        startActivity(intent);
    }
}
