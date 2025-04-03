package com.example.uni.acts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;

public class start_act extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_act);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        // Set the action for the "Get Started" button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action to perform when "Get Started" is clicked
                // Redirect to Login or next activity, for example
                Intent intent = new Intent(start_act.this, main_act.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
