package com.example.uni.management;

import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

public class fireStoreUtil {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FirebaseFirestore getDb(){
        return db;
    }

}
