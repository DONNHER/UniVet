package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.example.uni.R;
import com.example.uni.adapters.appAdapt;
import com.example.uni.entities.Appointment;
import com.example.uni.helper.TempStorage;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class OwnerDashboardAct extends AppCompatActivity {
    private  RecyclerView appointmentsView;
    private  RecyclerView appointmentsView2;
    private appAdapt appAdaptP;
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private final ArrayList<Appointment> appointmentsP = new ArrayList<>();
    private static final TempStorage temp = TempStorage.getInstance();

    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_d_board);
        appointmentsView = findViewById(R.id.appointmentsView);
        appointmentsView.setVisibility(View.VISIBLE);
        appointmentsView2 = findViewById(R.id.appointmentsView2);
        appointmentsView2.setVisibility(View.INVISIBLE);
          TextView name = findViewById(R.id.name1);
          if(myAuth.getCurrentUser() != null) {
              name.setText("Hi, " + myAuth.getCurrentUser().getDisplayName());
          }else {
              name.setText("Hi, " +TempStorage.getInstance().getIsLoggedIn().getName());
          }
          appointments();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void onMedClick(View view) {
        Intent intent = new Intent(this, medServiceAct.class); // Replace with actual target
        startActivity(intent);
    }
    public void onGroomClick(View view) {
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
    public void onPendingClick(View view) {
        appointmentsView.setVisibility(View.INVISIBLE);
        appointmentsView2.setVisibility(View.VISIBLE);
    }
    public void onUpcomingClick(View view) {
        appointmentsView.setVisibility(View.VISIBLE);
        appointmentsView2.setVisibility(View.INVISIBLE);
    }
    public void onBtnClick(View view) {
        Intent intent = new Intent(this, ownerDashB_setting.class); // Replace with actual target
        startActivity(intent);
    }

    private void appointments(){
        if ( temp.getAppointments().isEmpty()){
            return;
        }
        for (Appointment appointment : temp.getAppointments()){
            if (appointment.getStatus().equals("Pending")){
                appointmentsP.add(appointment);
            }else {
                appointments.add(appointment);
            }
        }
        com.example.uni.adapters.appAdapt appAdapt = new appAdapt(appointments);
        appAdaptP = new appAdapt(appointmentsP);
        appointmentsView.setAdapter(appAdapt);
        appointmentsView2.setAdapter(appAdaptP);
        appointmentsView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsView2.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onResume(){
        super.onResume();
        pAppointment();
    }
    public void pAppointment(){
        if ( temp.getPAppointments().isEmpty()) {
            return;
        }
        appAdaptP = new appAdapt(temp.getPAppointments());
        appointmentsView2.setAdapter(appAdaptP);
        appointmentsView.setLayoutManager(new LinearLayoutManager(this));
        appointmentsView2.setLayoutManager(new LinearLayoutManager(this));
    }
}
