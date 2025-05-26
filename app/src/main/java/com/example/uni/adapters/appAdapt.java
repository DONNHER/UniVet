package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.SectionItem;
import com.example.uni.fragments.AppointmentDetails;
import com.example.uni.fragments.Appointment_manage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class appAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SectionItem> sectionItems = new ArrayList<>();
    FragmentActivity fragmentManager;
    FragmentManager Manager;
    private final FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("NotifyDataSetChanged")
    public void setAppointments(Map<String, List<Appointment>> groupedAppointments, FragmentActivity fragmentManager, FragmentManager Manager) {
        this.fragmentManager = fragmentManager;
        this.Manager = Manager;
        sectionItems.clear();

        for (String date : groupedAppointments.keySet()) {
            sectionItems.add(new SectionItem(date)); // Header
            for (Appointment appointment : groupedAppointments.get(date)) {
                sectionItems.add(new SectionItem(appointment)); // Item
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionItems.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SectionItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment, parent, false);
            return new AppointmentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SectionItem sectionItem = sectionItems.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(sectionItem.date);
            return;
        }

        Appointment appointment = sectionItem.getAppointment();
        AppointmentViewHolder appHolder = (AppointmentViewHolder) holder;
        appHolder.bind(appointment);

        String currentActivity = fragmentManager.getClass().getSimpleName();
        boolean isTechnician = currentActivity.equals("TechnicianDashB");
        boolean isOwner = currentActivity.equals("OwnerDashboardAct");
        boolean isConfirmed = "confirmed".equalsIgnoreCase(appointment.getStatus());

        // Default state
        holder.itemView.setAlpha(1.0f);
        holder.itemView.setClickable(true);
        holder.itemView.setFocusable(true);

        // Handle click restrictions
        if (isTechnician && isConfirmed) {
            // Disable click for confirmed appointments in Technician view
            holder.itemView.setAlpha(0.6f);
            holder.itemView.setClickable(false);
            holder.itemView.setFocusable(false);
            holder.itemView.setOnClickListener(null);
        } else {
            // Owner or Technician (non-confirmed) can click
            holder.itemView.setOnClickListener(v -> {
                Intent intent;
                if (isOwner) {
                    intent = new Intent(fragmentManager, AppointmentDetails.class);
                    intent.putExtra("appointmentId", appointment.getId());
                    intent.putExtra("date", appointment.getAppointmentDate());
                    intent.putExtra("time", appointment.getAppointmentTime());
                    intent.putExtra("price", appointment.getPrice());
                    intent.putExtra("name", appointment.getPatientName());
                    intent.putExtra("services", appointment.getServices());
                } else {
                    // Technician view (allowed only if not confirmed)
                    intent = new Intent(fragmentManager, Appointment_manage.class);
                    intent.putExtra("appointmentId", appointment.getId());
                    intent.putExtra("date", appointment.getAppointmentDate());
                    intent.putExtra("time", appointment.getAppointmentTime());
                    intent.putExtra("price", appointment.getPrice());
                    intent.putExtra("name", appointment.getPatientName());
                    intent.putExtra("services", appointment.getServiceID());
                }

                intent.putExtra("status", appointment.getStatus());
                intent.putExtra("userID", appointment.getUserID());
                fragmentManager.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return sectionItems.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
        }

        public void bind(String date) {
            dateText.setText(date);
        }
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView time;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(Appointment appointment) {
            time.setText(appointment.getPatientName() + "     " + appointment.getStatus() + "      " + appointment.getAppointmentTime());
        }
    }
}
