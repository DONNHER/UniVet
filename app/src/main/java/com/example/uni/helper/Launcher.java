//package com.example.uni.helper;
//
//import android.annotation.SuppressLint;
//import android.widget.Toast;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.uni.R;
//import com.example.uni.adapters.GroomServiceAdaptor;
//import com.example.uni.adapters.ownerAdapt;
//import com.example.uni.entities.ServiceType;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//
//import java.util.ArrayList;
//
//public class Launcher {
//    private RecyclerView recyclerView;
//    private static TempStorage temp = TempStorage.getInstance();
//    private ArrayList<ServiceType> list;
//
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    @SuppressLint("NotifyDataSetChanged")
//    private void loadServices(String ids,) {
//        recyclerView =  findViewById(R.id.ids);
//        list = new ArrayList<>();
//        Adaptor = new ownerAdapt(list);
//        db.
//        collection("serviceType").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                list.clear();
//                for (QueryDocumentSnapshot d : task.getResult()) {
//                    ServiceType serviceType = d.toObject(ServiceType.class);
//                    list.add(serviceType);
//                    owner_adapt.notifyDataSetChanged();
//                }
//            } else {
//                Toast.makeText(getApplicationContext(), "error:2", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
