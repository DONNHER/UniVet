package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Appointment;
import com.example.uni.entities.SectionItem;
import com.example.uni.fragments.AppointmentDetails;
import com.example.uni.fragments.ownerLoginAct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class appAdapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SectionItem> sectionItems = new ArrayList<>();
    FragmentManager fragmentManager;
    private final FirebaseAuth myAuth= FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("NotifyDataSetChanged")
    public void setAppointments(Map<String, List<Appointment>> groupedAppointments, FragmentManager fragmentManager) {
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

        holder.itemView.setOnClickListener(v -> {
            AppointmentDetails dialogFragment = new AppointmentDetails();
            dialogFragment.show(fragmentManager, "AppointmentDialog");
        });
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
