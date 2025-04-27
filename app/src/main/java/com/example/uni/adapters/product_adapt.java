package com.example.uni.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.acts.TechHome;
import com.example.uni.entities.Item;
import com.example.uni.entities.Service;
import com.example.uni.fragments.product_management;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class product_adapt  extends   RecyclerView.Adapter<product_adapt.ViewHolder> {
    private ArrayList<Item> items;
    private FragmentActivity fragmentActivity;
    public product_adapt(ArrayList<Item> items, FragmentActivity fragmentActivity){
        this.items = items;
        this.fragmentActivity = fragmentActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.item),parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.price.setText(""+ item.getPrice());
        holder.name.setText(item.getName());
        holder.slots.setText(item.getSlot()+" units");
        Glide.with(holder.pic.getContext())
                .load(item.getImage())
                .into(holder.pic);
        holder.itemView.setOnClickListener(view -> {
            product_management dialogFragment = new product_management();
            dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "AppointmentDialog");
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price;
        public ImageView pic;
        public TextView name;
        public TextView slots;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            price = itemView.findViewById(R.id.price2);
            pic = itemView.findViewById(R.id.image2);
            name = itemView.findViewById(R.id.service_name1);
            slots = itemView.findViewById(R.id.slots);
        }
    }
}

