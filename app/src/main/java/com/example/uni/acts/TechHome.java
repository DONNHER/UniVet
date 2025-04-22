package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.fragments.Menu;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

public class TechHome  extends AppCompatActivity {
    private RecyclerView recyclerView = findViewById(R.id.appointmentsView);
    private RecyclerView recyclerView2= findViewById(R.id.appointmentsView2);
    private static  TempStorage temp = TempStorage.getInstance();
    private FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tech_home);
        services();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void sideMenu(View view) {
        Menu menu = new Menu();
        menu.show(menu.getParentFragmentManager(), "MenuDialog");
    }

    public FragmentManager getFragment(){
        return getSupportFragmentManager();
    }

    private void services(){
    }
}

