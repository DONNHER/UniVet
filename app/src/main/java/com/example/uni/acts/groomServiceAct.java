package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.helper.TempStorage;
import com.example.uni.serviceType;
import com.example.uni.viewModel.ownerVModel;
import com.google.firebase.auth.FirebaseAuth;
//import com.example.uni.management.SessionManager;

public  class groomServiceAct extends AppCompatActivity {
//    private ownerVModel ownerVModel;
    private static final TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private static final serviceType.Services serviceType = com.example.uni.serviceType.Services.grooming ;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Main UI for the logged-in user
        setContentView(R.layout.groom_service);
//        ownerVModel = new ViewModelProvider(this).get(ownerVModel.class);
//        TextView cost = findViewById(R.id.cost);
//        TextView cost1 = findViewById(R.id.cost1);
//        ownerVModel.getuserdata().observe(this, owner -> {
        if (myAuth.getCurrentUser() != null) {
            TextView name = findViewById(R.id.name2);//
            name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
            return;
        }
//            Toast.makeText(getApplicationContext(),"I" + owner.getEmail() + owner.getPassword(),Toast.LENGTH_SHORT).show();
//        });
        TextView name = findViewById(R.id.name2);//
        name.setText( "Hi, ");
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

    private void loadUserRoleUI() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        Intent intent;
        if ("admin".equals(role)) {
            intent = new Intent(this, OwnerDashboardAct.class);
//        else if ("technician".equals(role)) {
//            intent = new Intent(this, TechnicianDashboardActivity.class);
//        } else {
//            intent = new Intent(this, OwnerDashboardActivity.class);
//        }
            startActivity(intent);
            finish(); // Close MainActivity after redirection

        }
    }
    public void onTrimClick(View view) {
        if(myAuth.getCurrentUser()  == null){
            ownerLoginAct dialogFragment = new ownerLoginAct();
            dialogFragment.show(getSupportFragmentManager(), "LogInDialog");
            return;
        }
        appAct dialogFragment = new appAct();
        dialogFragment.show(getSupportFragmentManager(), "appointmentDialog");
    }
    public void onCleanClick(View view) {
        if(myAuth.getCurrentUser()  == null){
            ownerLoginAct dialogFragment = new ownerLoginAct();
            dialogFragment.show(getSupportFragmentManager(), "LogInDialog");
            return;
        }
        appAct dialogFragment = new appAct();
        dialogFragment.show(getSupportFragmentManager(), "appointmentDialog");
    }
    public void onResClick(View view) {
        ownerRegisterAct dialogFragment = new ownerRegisterAct();
        dialogFragment.show(dialogFragment.getParentFragmentManager(), "RegisterDialog");
    }

    public static serviceType.Services getServiceType() {
        return serviceType;
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }
}
