package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.acts.GroomPackage;
import com.example.uni.acts.MedPackage;
import com.example.uni.acts.TechHome;
import com.example.uni.acts.groomServiceAct;
import com.example.uni.acts.medServiceAct;
import com.example.uni.entities.ServiceType;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GroomServiceAdaptor  extends RecyclerView.Adapter<GroomServiceAdaptor.manageServiceTypeHolder> {
    private ArrayList<ServiceType> appointments;


    @SuppressLint("NotifyDataSetChanged")
    public GroomServiceAdaptor(ArrayList<ServiceType> items){
        this.appointments = items;
    }


    @NonNull
    @Override
    public manageServiceTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.service_type),parent,false);
        return new manageServiceTypeHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull manageServiceTypeHolder holder, int position) {
        ServiceType item = appointments.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(""+item.getDescription());
        Log.d("ServiceTypeDebug"+item.getDescription(), item.toString());
        Glide.with(holder.pic.getContext())
                .load(item.getImage())
                .into(holder.pic);
        if (item.getName().trim().equalsIgnoreCase("Grooming")) {
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), GroomPackage.class);
                view.getContext().startActivity(intent);
            });
        } else {
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), MedPackage.class);
                view.getContext().startActivity(intent);
            });
        }
        Log.d("ServiceTypeName", "Name: '" + item.getName() + "'");
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class manageServiceTypeHolder extends RecyclerView.ViewHolder {
        public ImageView pic;
        public TextView name;
        public TextView description;
        public manageServiceTypeHolder(@NonNull View itemView){
            super(itemView);
            description = itemView.findViewById(R.id.textView2);
            pic = itemView.findViewById(R.id.image3);
            name = itemView.findViewById(R.id.service_name);
        }
    }
}
