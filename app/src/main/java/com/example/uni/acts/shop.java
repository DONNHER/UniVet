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
import com.example.uni.adapters.product_adapt;
import com.example.uni.entities.Item;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.AddServiceType;
import com.example.uni.fragments.userMenu;
import com.example.uni.fragments.product_management;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class shop extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Item> list;
    private product_adapt Adaptor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products);
        recyclerView = findViewById(R.id.Products_Recycler);
        list = new ArrayList<>();
        Adaptor = new product_adapt(list,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Adaptor);
        loadServices();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadServices();
    }

    public void onside(View view) {
        userMenu menu = new userMenu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }


    @SuppressLint("NotifyDataSetChanged")
    private void loadServices() {
        db.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                list.clear();
                for (QueryDocumentSnapshot d : task.getResult()) {
                    Item newItem = d.toObject(Item.class);
                    list.add(newItem);
                    Adaptor.notifyDataSetChanged();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
