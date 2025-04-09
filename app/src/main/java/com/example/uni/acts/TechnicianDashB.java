package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uni.R;
import com.example.uni.fragments.Menu;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;

public class TechnicianDashB extends AppCompatActivity {
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technician_dash);
        TextView name = findViewById(R.id.name);
        if(myAuth.getCurrentUser() != null) {
            name.setText("Hi, " + myAuth.getCurrentUser().getDisplayName());
        }else {
            name.setText("Hi, " + TempStorage.getInstance().getIsLoggedIn().getName());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onServiceManageClick(View view) {
        Intent intent = new Intent(this, ManageServiceType.class); // Replace with actual target
        startActivity(intent);
    }
    public void onProductManageClick(View view) {
        Intent intent = new Intent(this, groomServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onProductClick(View view) {
        Intent intent = new Intent(this, productServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onOtherClick(View view) {
        Intent intent = new Intent(this, otherServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onMenuClick(View view) {
        Menu menu = new Menu();
        menu.show(menu.getParentFragmentManager(), "MenuDialog");
    }
}
