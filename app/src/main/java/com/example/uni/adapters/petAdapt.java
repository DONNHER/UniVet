package com.example.uni.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.R;
import com.example.uni.entities.Pet;

import java.util.ArrayList;

public class petAdapt extends RecyclerView.Adapter<petAdapt.PetViewHolder> {
    
    private ArrayList<Pet> petList;
    private Context context;
    private OnPetClickListener listener;
    
    public interface OnPetClickListener {
        void onPetClick(Pet pet, int position);
    }
    
    public petAdapt(ArrayList<Pet> petList) {
        this.petList = petList != null ? petList : new ArrayList<>();
    }
    
    public petAdapt(ArrayList<Pet> petList, Context context) {
        this.petList = petList != null ? petList : new ArrayList<>();
        this.context = context;
    }
    
    public void setOnPetClickListener(OnPetClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        // TODO: Create pet_item_layout.xml
        // For now, use a simple layout
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new PetViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        if (petList != null && position < petList.size()) {
            Pet pet = petList.get(position);
            holder.bind(pet);
            
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPetClick(pet, position);
                }
            });
        }
    }
    
    @Override
    public int getItemCount() {
        return petList != null ? petList.size() : 0;
    }
    
    public void updatePetList(ArrayList<Pet> newPetList) {
        this.petList = newPetList != null ? newPetList : new ArrayList<>();
        notifyDataSetChanged();
    }
    
    public void addPet(Pet pet) {
        if (pet != null) {
            petList.add(pet);
            notifyItemInserted(petList.size() - 1);
        }
    }
    
    public void removePet(int position) {
        if (position >= 0 && position < petList.size()) {
            petList.remove(position);
            notifyItemRemoved(position);
        }
    }
    
    public static class PetViewHolder extends RecyclerView.ViewHolder {
        
        // TODO: Replace with actual views when proper layout is created
        private TextView petNameText;
        private TextView petDetailsText;
        private ImageView petImage;
        
        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            
            // TODO: Initialize views when pet_item_layout.xml is created
            // petNameText = itemView.findViewById(R.id.pet_name);
            // petDetailsText = itemView.findViewById(R.id.pet_details);
            // petImage = itemView.findViewById(R.id.pet_image);
            
            // For now, use simple list item views
            petNameText = itemView.findViewById(android.R.id.text1);
            petDetailsText = itemView.findViewById(android.R.id.text2);
        }
        
        public void bind(Pet pet) {
            if (pet != null) {
                if (petNameText != null) {
                    petNameText.setText(pet.getName() != null ? pet.getName() : "Unknown Pet");
                }
                
                if (petDetailsText != null) {
                    String details = "";
                    if (pet.getBreed() != null) {
                        details += "Breed: " + pet.getBreed();
                    }
                    if (pet.getAge() != null) {
                        details += (details.isEmpty() ? "" : " | ") + "Age: " + pet.getAge();
                    }
                    petDetailsText.setText(details.isEmpty() ? "No details available" : details);
                }
                
                // TODO: Load pet image when petImage view is available
                // if (petImage != null && pet.getImageUrl() != null) {
                //     Glide.with(itemView.getContext())
                //         .load(pet.getImageUrl())
                //         .placeholder(R.drawable.pet_placeholder)
                //         .into(petImage);
                // }
            }
        }
    }
}