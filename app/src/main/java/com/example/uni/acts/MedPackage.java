package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.packageAdapt;
import com.example.uni.entities.Service;
import com.example.uni.fragments.addService;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.ownerRegisterAct;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MedPackage  extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Service> list;
    private packageAdapt Adaptor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_packages);
        recyclerView =  findViewById(R.id.medPackages);
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
        Intent intent = new Intent(this, ownerLoginAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, settingAct.class); // Replace with actual target
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void onAddMedService(View view) {
        addService dialog = new addService();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }
    public FragmentManager getFragment(){
        return getSupportFragmentManager();
    }
    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {

        db.collection("serviceType").document("Medical").collection("package").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    Service service = d.toObject(Service.class);
                    list.add(service);
                    Adaptor.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error:3", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
