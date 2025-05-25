package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.adapters.GroomServiceAdaptor;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.AddServiceType;
import com.example.uni.fragments.Menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class serviceAct extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ServiceType> list;
    private GroomServiceAdaptor groomServiceAdaptor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_servicetype);
        recyclerView = findViewById(R.id.Recycler2);
        list = new ArrayList<>();
        groomServiceAdaptor = new GroomServiceAdaptor(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(groomServiceAdaptor);
        loadServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadServices();
    }

    public void onside(View view) {
        Menu menu = new Menu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }

    public void add(View view) {
        AddServiceType dialog = new AddServiceType();
        dialog.show(getSupportFragmentManager(), "AddDialog");
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    ServiceType serviceType = d.toObject(ServiceType.class);
                    list.add(serviceType);
                    groomServiceAdaptor.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
