package com.example.uni.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni.MainActivity;
import com.example.uni.R;
import com.example.uni.adapters.ownerAdapt;
import com.example.uni.entities.ServiceType;
import com.example.uni.fragments.start_act;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class WelcomeFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView servicesRecyclerView;
    private ArrayList<ServiceType> servicesList;
    private ownerAdapt servicesAdapter;
    private Button btnLogin, btnRegister, btnGuest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        servicesList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        
        initializeViews(view);
        setupRecyclerView();
        loadServices();
        setupClickListeners();
        
        // Hide bottom navigation for welcome screen
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideBottomNavigation();
        }
        
        return view;
    }

    private void initializeViews(View view) {
        servicesRecyclerView = view.findViewById(R.id.services_recycler_view);
        btnLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.btn_register);
        btnGuest = view.findViewById(R.id.btn_guest);
    }

    private void setupRecyclerView() {
        servicesAdapter = new ownerAdapt(servicesList);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        servicesRecyclerView.setAdapter(servicesAdapter);
    }

    private void loadServices() {
        db.collection("serviceType").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && isAdded()) {
                servicesList.clear();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    ServiceType serviceType = doc.toObject(ServiceType.class);
                    servicesList.add(serviceType);
                }
                servicesAdapter.notifyDataSetChanged();
            } else {
                if (isAdded()) {
                    Toast.makeText(getContext(), "Error loading services", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupClickListeners() {
        btnLogin.setOnClickListener(v -> showLoginDialog());
        btnRegister.setOnClickListener(v -> showRegisterDialog());
        btnGuest.setOnClickListener(v -> continueAsGuest());
    }

    private void showLoginDialog() {
        start_act loginDialog = new start_act(getActivity());
        loginDialog.show(getParentFragmentManager(), "LoginDialog");
    }

    private void showRegisterDialog() {
        start_act registerDialog = new start_act(getActivity());
        registerDialog.show(getParentFragmentManager(), "RegisterDialog");
    }

    private void continueAsGuest() {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.loadFragment(new ServicesFragment(), false);
            mainActivity.showBottomNavigation();
        }
    }
}