package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.fragments.Menu;
import com.example.uni.fragments.addService;
import com.google.firebase.auth.FirebaseAuth;

public class TechServicePackages extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_servicetype);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onside(View view) {
        Menu menu = new Menu();
        menu.show(getSupportFragmentManager(), "MenuDialog");
    }
    public void editClick(View view){
        addService dialog = new addService();
        dialog.show(getSupportFragmentManager(), "AddItemDialog");
    }
}
