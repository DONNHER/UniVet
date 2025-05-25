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
import com.example.uni.fragments.ownerLoginAct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class appAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SectionItem> sectionItems = new ArrayList<>();
    FragmentActivity fragmentManager;
    FragmentManager Manager;
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("NotifyDataSetChanged")
    public void setAppointments(Map<String, List<Appointment>> groupedAppointments, FragmentActivity fragmentManager,FragmentManager Manager) {
        this.fragmentManager = fragmentManager;
        sectionItems.clear();
        for (String date : groupedAppointments.keySet()) {
            sectionItems.add(new SectionItem(date)); // Add date header
            for (Appointment appointment : groupedAppointments.get(date)) {
                sectionItems.add(new SectionItem(appointment)); // Add appointments under that date
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
        if(fragmentManager.getClass().getSimpleName().equals("TechnicianDashB")){
            holder.itemView.setOnClickListener(v -> {
                Intent i = new Intent(fragmentManager, Appointment_manage.class);
                i.putExtra("appointmentId", sectionItem.getAppointment().getId());
                i.putExtra("date", sectionItem.getAppointment().getAppointmentDate());
                i.putExtra("time", sectionItem.getAppointment().getAppointmentTime());
                i.putExtra("totalCost", sectionItem.getAppointment().getTotalCost());
                i.putExtra("name", sectionItem.getAppointment().getPatientName());
                i.putExtra("services", sectionItem.getAppointment().getServiceID());
                i.putExtra("status", sectionItem.getAppointment().getStatus());
                i.putExtra("userID", sectionItem.getAppointment().getUserID());
                fragmentManager.startActivity(i);
            });
        }else {
            holder.itemView.setOnClickListener(v -> {
                Intent i = new Intent(fragmentManager, AppointmentDetails.class);
                fragmentManager.startActivity(i);
            });
        }
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(sectionItem.date);
        } else if (holder instanceof AppointmentViewHolder) {
            ((AppointmentViewHolder) holder).bind(sectionItem.appointment);
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
            dateText.setText(date); // Set the date for the header
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
