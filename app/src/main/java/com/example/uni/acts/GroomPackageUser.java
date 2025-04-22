package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.ownerAdapt;
import com.example.uni.adapters.packageAdapt;
import com.example.uni.entities.Service;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.addService;
import com.example.uni.fragments.groomAppointments;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class GroomPackageUser  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Service> list;
    private packageAdapt Adaptor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static com.example.uni.management.serviceType.Services.Grooming serviceType;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groom_pakage_user);
        recyclerView =  findViewById(R.id.groomPackages);
        list = new ArrayList<>();
        Adaptor = new packageAdapt(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adaptor);
        loadServices();
    }
    public void back(View view) {
        finish();
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
    @Override
    protected void onResume(){
        super.onResume();
    }
    public FragmentManager getFragment(){
        return getSupportFragmentManager();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {
        db.collection("serviceType").document("Grooming").collection("package").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    Service serviceType = d.toObject(Service.class);
                    list.add(serviceType);
                    Adaptor.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
