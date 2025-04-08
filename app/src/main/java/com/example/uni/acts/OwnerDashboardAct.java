package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import com.example.uni.R;
import com.example.uni.adapters.appAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.owner;
import com.example.uni.helper.TempStorage;
import com.example.uni.viewModel.ownerVModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
//import com.example.uni.management.SessionManager;

public class OwnerDashboardAct extends AppCompatActivity {
//    private ownerVModel ownerVModel;
    private  RecyclerView appointmentsView;
    private  RecyclerView appointmentsView2;
    private appAdapt appAdapt;
    private appAdapt appAdaptP;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<Appointment> appointmentsP = new ArrayList<>();
    private static  TempStorage temp = TempStorage.getInstance();

    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        if (!isSessionActive()) {
//            // Redirect to Login if session is not found
//            Intent intent = new Intent(this, ownerLoginAct.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//            return; // Prevent further execution
//        }

        // Load Main UI for the logged-in user
        setContentView(R.layout.user_d_board);
        appointmentsView = findViewById(R.id.appointmentsView);
        appointmentsView.setVisibility(View.VISIBLE);
        appointmentsView2 = findViewById(R.id.appointmentsView2);
        appointmentsView2.setVisibility(View.INVISIBLE);
//        ownerVModel = new ViewModelProvider(this).get(ownerVModel.class);
          TextView name = findViewById(R.id.name1);
          if(myAuth.getCurrentUser() != null) {
              name.setText("Hi, " + myAuth.getCurrentUser().getDisplayName());
          }else {
              name.setText("Hi, " +TempStorage.getInstance().getIsLoggedIn().getName());
          }
          appointments();
    }
    private void initializeServices() {

//        com.univet.service.PetService petService = new com.univet.service.PetService(dbHelper);
//        com.univet.service.OwnerService ownerService = new com.univet.service.OwnerService(dbHelper);
//        com.univet.service.ServiceService serviceService = new com.univet.service.ServiceService(dbHelper);
//        com.univet.service.AppointmentService appointmentService = new com.univet.service.AppointmentService(dbHelper);
//
//        // Initialize SessionManager
//        SessionManager.initialize(petService, ownerService, serviceService, appointmentService);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SessionManager.getInstance().saveSessionData();
    }

    // Check if a session exists
    private boolean isSessionActive() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void onMedClick(View view) {
        Intent intent = new Intent(this, medServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onGroomClick(View view) {
        Intent intent = new Intent(this, groomServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onProductClick(View view) {
        Intent intent = new Intent(this, productServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onOtherClick(View view) {
        Intent intent = new Intent(this, otherServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onPendingClick(View view) {
        appointmentsView.setVisibility(View.INVISIBLE);
        appointmentsView2.setVisibility(View.VISIBLE);
    }
    public void onUpcomingClick(View view) {
        appointmentsView.setVisibility(View.VISIBLE);
        appointmentsView2.setVisibility(View.INVISIBLE);
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, ownerDashB_setting.class); // Replace with actual target
        startActivity(intent);
    }

    private void appointments(){
        if ( temp.getAppointments().isEmpty()){
            return;
        }
        for (Appointment appointment : temp.getAppointments()){
            if (appointment.getStatus().equals("Pending")){
                appointmentsP.add(appointment);
            }else {
                appointments.add(appointment);
            }
        }
        appAdapt = new appAdapt(appointments);
        appAdaptP = new appAdapt(appointmentsP);
        appointmentsView.setAdapter(appAdapt);
        appointmentsView2.setAdapter(appAdaptP);
        appointmentsView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsView2.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onResume(){
        super.onResume();
        pAppointment();
    }
    public void pAppointment(){
        if ( temp.getPAppointments().isEmpty()) {
            return;
        }
        appAdaptP = new appAdapt(temp.getPAppointments());
        appointmentsView2.setAdapter(appAdaptP);
        appointmentsView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsView2.setLayoutManager(new LinearLayoutManager(this));
    }
}
