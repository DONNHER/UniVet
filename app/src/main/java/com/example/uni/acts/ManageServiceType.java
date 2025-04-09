package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.owner;
import com.example.uni.fragments.Menu;
import com.example.uni.fragments.ownerRegisterAct;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

public class ManageServiceType extends AppCompatActivity {
    private static owner logged = null;
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
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
}
