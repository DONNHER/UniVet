package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uni.R;
import com.example.uni.adapters.serviceAdapt;
import com.example.uni.entities.Service;
import com.example.uni.fragments.*;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public  class groomServiceAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static serviceAdapt serviceAdapt;
    private static  TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();


    private static com.example.uni.management.serviceType.Services.Grooming serviceType;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groom_service);
        recyclerView =  findViewById(R.id.service_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

//        if (temp.getIsLoggedIn().getClass().getSimpleName().equals("owner") || myAuth.getCurrentUser().getClass().getSimpleName().equals("owner")){
//            add.setVisibility(View.INVISIBLE);
//        }
        if (myAuth.getCurrentUser() != null) {
            TextView name = findViewById(R.id.name2);//
            name.setText( "Hi, " + myAuth.getCurrentUser().getDisplayName());
            return;
        }
        TextView name = findViewById(R.id.name2);//
        name.setText( "Hi, ");
        services();
    }
    public void back(View view) {
        finish();
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
    public void onAddClick(View view) {
        addService dialog = new addService();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }
    public FragmentManager getFragment(){
        return getSupportFragmentManager();
    }
}
