package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.adapters.appAdapt;
import com.example.uni.adapters.serviceAdapt;
import com.example.uni.entities.Admin;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.Service;
import com.example.uni.entities.Technician;
import com.example.uni.entities.owner;
import com.example.uni.fragments.appAct;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.helper.TempStorage;
import com.example.uni.management.serviceType;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
//import com.example.uni.management.SessionManager;

public  class groomServiceAct extends AppCompatActivity {
//    private ownerVModel ownerVModel;

    private RecyclerView recyclerView;
    private static serviceAdapt serviceAdapt;

    private ArrayList<Service> services = new ArrayList<>();
    private ArrayList<Service> nServices = new ArrayList<>();
    private static  TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private static com.example.uni.management.serviceType.Services.Grooming serviceType;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load Main UI for the logged-in user
        setContentView(R.layout.groom_service);

        recyclerView =  findViewById(R.id.service_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        ImageView add = findViewById(R.id.add);
        if (temp.getIsLoggedIn().getClass().getSimpleName().equals("owner")){
            add.setVisibility(View.INVISIBLE);
        }

        if (myAuth.getCurrentUser() != null) {
            TextView name = findViewById(R.id.name2);//
            name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
            return;
        }
//            Toast.makeText(getApplicationContext(),"I" + owner.getEmail() + owner.getPassword(),Toast.LENGTH_SHORT).show();
//        });
        TextView name = findViewById(R.id.name2);//
        name.setText( "Hi, ");
        services();
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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        SessionManager.getInstance().saveSessionData();
//    }
    public void back(View view) {
        finish();
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
        if(myAuth.getCurrentUser()  == null && TempStorage.getInstance().getIsLoggedIn()== null){
            setServiceType(com.example.uni.management.serviceType.Services.Grooming.Trimming);
            ownerLoginAct dialogFragment = new ownerLoginAct();
            dialogFragment.show(getSupportFragmentManager(), "LogInDialog");
            return;
        }
        appAct dialogFragment = new appAct();
        dialogFragment.show(getSupportFragmentManager(), "appointmentDialog");
    }
    public void onCleanClick(View view) {
        if(myAuth.getCurrentUser()  == null && TempStorage.getInstance().getIsLoggedIn() == null){
            setServiceType(com.example.uni.management.serviceType.Services.Grooming.Cleaning);
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
    public static com.example.uni.management.serviceType.Services.Grooming getServiceType() {
        return serviceType;
    }
    public static void setServiceType(com.example.uni.management.serviceType.Services.Grooming service){
        serviceType = service;
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }
    private void services(){
        temp.addService(new Service(200, R.drawable.add,"Trimming"));
        temp.addService(new Service(200, R.drawable.add,"Trimming"));
        temp.addService(new Service(200, R.drawable.add,"Trimming"));
        if ( temp.getServices().isEmpty()){
            return;
        }
        serviceAdapt = new serviceAdapt(temp.getServices());
        recyclerView.setAdapter(serviceAdapt);
    }
    @Override
    protected void onResume(){
        super.onResume();
        newServices();
    }
    public void newServices(){
        if ( temp.getNServices().isEmpty()) {
            return;
        }
        serviceAdapt = new serviceAdapt(temp.getNServices());
        recyclerView.setAdapter(serviceAdapt);
    }
}
