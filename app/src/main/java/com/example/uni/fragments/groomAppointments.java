package com.example.uni.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class groomAppointments extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private FirebaseFirestore db;

    private String id;
    private String Service_name;
    private String description;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_act);

        myAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get service data from intent
        id = getIntent().getStringExtra("id");
        Service_name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        price = getIntent().getDoubleExtra("price", 0.0);

        // Views
        TextView serviceName = findViewById(R.id.serviceName);
        TextView serviceDescription = findViewById(R.id.serviceDescription);
        TextView priceText = findViewById(R.id.price9);
        EditText editTextMonth = findViewById(R.id.editTextMonth);
        EditText editTextDay = findViewById(R.id.editTextDay);
        EditText editTextYear = findViewById(R.id.editTextYear);
        EditText patientName = findViewById(R.id.namePatient);
        TextView timeTextView = findViewById(R.id.time);
        ImageView calendarIcon = findViewById(R.id.imageViewCalendar);
        ImageView timeIcon = findViewById(R.id.imageViewTime);
        Button scheduleButton = findViewById(R.id.scheduleButton);

        // Set service details
        serviceName.setText(Service_name);
        serviceDescription.setText(description);
        priceText.setText(String.valueOf(price));

        // Date feedback
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                String mm = editTextMonth.getText().toString();
                String dd = editTextDay.getText().toString();
                String yyyy = editTextYear.getText().toString();
                if (mm.length() == 2 && dd.length() == 2 && yyyy.length() == 4) {
                    String fullDate = mm + "/" + dd + "/" + yyyy;
                    Toast.makeText(groomAppointments.this, "Date Entered: " + fullDate, Toast.LENGTH_SHORT).show();
                }
            }
        };
        editTextMonth.addTextChangedListener(watcher);
        editTextDay.addTextChangedListener(watcher);
        editTextYear.addTextChangedListener(watcher);

        // Calendar Picker
        calendarIcon.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    groomAppointments.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate currentDate = LocalDate.now();
                            LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay);

                            if (selectedDate.isBefore(currentDate)) {
                                Toast.makeText(this, "Please select a future date.", Toast.LENGTH_SHORT).show();
                                editTextMonth.setText("");
                                editTextDay.setText("");
                                editTextYear.setText("");
                                return;
                            }

                            editTextMonth.setText(String.format("%02d", selectedMonth + 1));
                            editTextDay.setText(String.format("%02d", selectedDay));
                            editTextYear.setText(String.valueOf(selectedYear));
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Time Picker
        timeIcon.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    groomAppointments.this,
                    (view12, hourOfDay, minute1) -> {
                        String formattedTime = String.format("%02d:%02d", hourOfDay, minute1);
                        timeTextView.setText(formattedTime);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        // Schedule Button Logic
        scheduleButton.setOnClickListener(v -> {
            String mm = editTextMonth.getText().toString();
            String dd = editTextDay.getText().toString();
            String yyyy = editTextYear.getText().toString();
            String time = timeTextView.getText().toString();
            String patient = patientName.getText().toString();

            if (mm.isEmpty() || dd.isEmpty() || yyyy.isEmpty()) {
                Toast.makeText(this, "Please select a valid date.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (time.isEmpty()) {
                Toast.makeText(this, "Please enter a valid time.", Toast.LENGTH_SHORT).show();
                return;
            }

            String fullDate = mm + "/" + dd + "/" + yyyy;
            String docId = UUID.randomUUID().toString();

            Appointment appointment = new Appointment(
                    myAuth.getCurrentUser().getDisplayName(),
                    fullDate,
                    time,
                    patient
            );

            appointment.setId(docId);
            appointment.setStatus("Pending");
            appointment.setUserID(myAuth.getCurrentUser().getUid());
            appointment.setServiceID(id);
            Map<String, Object> data = new HashMap<>();
            data.put("id", appointment.getId());
            data.put("status", appointment.getStatus());
            data.put("userID", appointment.getUserID());
            data.put("serviceID", appointment.getServiceID());

            db.collection("users").document("user").collection("account").document(docId).collection("appointments").add(data)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Appointment scheduled successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close activity after scheduling
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
