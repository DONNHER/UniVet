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
import com.example.uni.entities.groomAppointment;
import com.example.uni.fragments.Menu;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

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
        TextView name = findViewById(R.id.name);
        name.setText("Hi, " + myAuth.getCurrentUser().getDisplayName());

        btn2.setOnClickListener(v ->filter("Pending"));
        btn1.setOnClickListener(v ->filter("Confirmed"));
        filter("Pending");
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
        db.collection("appointments").whereEqualTo("type","grooming").get().addOnSuccessListener(queryDocumentSnapshots -> {
            appointments.clear();
            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                groomAppointment appointment = documentSnapshot.toObject(groomAppointment.class);
               appointments.add(appointment);
        }
        });
    }

    private void filter(String s){
        ArrayList<Appointment> filtered = new ArrayList<>();
        for (Appointment appointment : appointments){
            if (appointment.getStatus().equals(s)){
                filtered.add(appointment);
            }
        }
        Adapt.setAppointments(filtered,getSupportFragmentManager());
    }
    public void onManageClick(View view) {
        Intent intent = new Intent(this, ManageServiceType.class); // Replace with actual target
        startActivity(intent);
    }
}
