package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.recyclerview.widget.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.example.uni.R;
import com.example.uni.adapters.appAdapt;
import com.example.uni.adapters.ownerAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.userMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OwnerDashboardAct extends AppCompatActivity {
    private  RecyclerView appointmentsView ;
    private appAdapt Adapt;
    private Button btn1, btn2;
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrayList<ServiceType> list;
    private ownerAdapt owner_adapt;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_d_board);
        recyclerView = findViewById(R.id.appointmentsView);
        list = new ArrayList<>();
        owner_adapt = new ownerAdapt(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(owner_adapt);
        loadServices();
        btn1 = findViewById(R.id.appoint1);
        btn2 = findViewById(R.id.appoint2);
        appointmentsView = findViewById(R.id.appointments23);
        Adapt = new appAdapt();
        appointmentsView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsView.setAdapter(Adapt);
        TextView name = findViewById(R.id.name);
        name.setText("Hi, " + myAuth.getCurrentUser().getDisplayName());
        btn2.setOnClickListener(v ->filter("Pending"));
        btn1.setOnClickListener(v ->filter("Confirmed"));
        filter("Pending");
        appointments();
    }

    private void appointments(){
        db.collection("Appointments").get().addOnSuccessListener(queryDocumentSnapshots -> {
            appointments.clear();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Appointment appointment = documentSnapshot.toObject(Appointment.class);
                appointments.add(appointment);
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

        // Group appointments by date and pass the grouped data to the adapter
        Map<String, List<Appointment>> groupedAppointments = groupByDate(filtered);
        Adapt.setAppointments(groupedAppointments, getSupportFragmentManager());
    }

    private Map<String, List<Appointment>> groupByDate(List<Appointment> appointments) {
        Map<String, List<Appointment>> grouped = new LinkedHashMap<>();
        for (Appointment appointment : appointments) {
            String date = appointment.getAppointmentDate(); // Assuming this method returns the date
            if (!grouped.containsKey(date)) {
                grouped.put(date, new ArrayList<>());
            }
            grouped.get(date).add(appointment);
        }
        return grouped;
    }

    public void onMenuClicks(View view) {
        userMenu menu = new userMenu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    ServiceType serviceType = d.toObject(ServiceType.class);
                    list.add(serviceType);
                    owner_adapt.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        loadServices();
    }
}
