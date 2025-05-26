package com.example.uni.fragments;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;

public class AppointmentDetails extends AppCompatActivity {
    private String date, time, name, services;
    private String totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmen_detials);

        // Retrieve data from intent
        if (getIntent() != null) {
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            totalCost = getIntent().getStringExtra("price");
            name = getIntent().getStringExtra("name");
            services = getIntent().getStringExtra("services");
        }

        // Bind data to views
        TextView Date = findViewById(R.id.Date);
        TextView Time = findViewById(R.id.Time);
        TextView tCost = findViewById(R.id.cost);
        TextView Name = findViewById(R.id.r);
        TextView service = findViewById(R.id.service);

        Date.setText(date);
        Time.setText(time);
        tCost.setText(totalCost);
        Name.setText(name);
        service.setText(services);
    }
}