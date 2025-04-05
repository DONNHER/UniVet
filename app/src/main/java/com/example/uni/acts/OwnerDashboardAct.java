package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.owner;
import com.example.uni.helper.TempStorage;
import com.example.uni.viewModel.ownerVModel;
import com.google.firebase.auth.FirebaseAuth;
//import com.example.uni.management.SessionManager;

public class OwnerDashboardAct extends AppCompatActivity {
//    private ownerVModel ownerVModel;
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
//        ownerVModel = new ViewModelProvider(this).get(ownerVModel.class);

//      ownerVModel.getuserdata().observe(this, owner -> {
          TextView name = findViewById(R.id.name1);
          name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
//      });
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
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }

    private void appointments(){
        Toast.makeText(getApplicationContext()," "+ temp.getAppointments().size(),Toast.LENGTH_SHORT).show();
        TextView app = findViewById(R.id.appointments);
        String s = app.getText().toString();
        for(Appointment appointment: temp.getAppointments()){
            s += appointment.toString() +"\n";
            Toast.makeText( getApplicationContext()," " + appointment,Toast.LENGTH_SHORT).show();
        }
        app.setText(s);
    }
}
