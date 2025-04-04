package com.example.uni.acts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni.adapters.recycler;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.Item;
import com.example.uni.entities.Service;

import java.util.ArrayList;

public class appAct extends DialogFragment {

    private ImageView doctorImage;
    private TextView doctorInfo;
    private CalendarView calendarView;
    private Button scheduleButton;
    private recycler recycler;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ArrayList<Item> items;

    private TempStorage temp = ownerLoginAct.getTemp() ;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.app_act, container, false); // Ensure this matches your XML file name
//        recyclerView = view.findViewById(R.id.recycleView);
//        recyclerView2 = view.findViewById(R.id.recycleView2);
        // Initialize UI elements

        calendarView = view.findViewById(R.id.calendarView);
        scheduleButton = view.findViewById(R.id.scheduleButton);
        EditText time = view.findViewById(R.id.time);
        scheduleButton.setOnClickListener(v -> {
            // Handle button click to schedule appointment
            if (time == null || time.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }

            String dateTime = calendarView.getDate() + " " + time.getText().toString();
            Appointment newAppointment = new Appointment(
                    OwnerDashboardAct.getLogged().getEmail(),
                    groomServiceAct.getServiceType(),
                    dateTime
            );
            temp.addAppointment(newAppointment);
            Toast.makeText(getContext(), "Successful Appointment", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        return view;
    }
//
//    private void setupSlots(GridLayout gridLayout, String timeOfDay) {
//        // You can dynamically populate time slots based on the grid layout for morning or evening
//        // Example: Add buttons for time slots dynamically
//        for (int i = 0; i < 4; i++) {
//            Button slotButton = new Button(getActivity());
//            slotButton.setText(timeOfDay + " Slot " + (i + 1));
//            // Set up button listeners if needed
//            gridLayout.addView(slotButton);
//        }
//    }
}
