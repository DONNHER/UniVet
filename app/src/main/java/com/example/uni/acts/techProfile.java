package com.example.uni.acts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.fragments.start_act;
import com.example.uni.helper.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class techProfile extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Uri selectedImageUri;
    private ImageView imageView, edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);

        imageView = findViewById(R.id.profile_image);
        edit = findViewById(R.id.edit);
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(v->{
            myAuth.signOut();
            startActivity(new Intent(this, main_act.class));
            finish();
        });
        FirebaseUser user = myAuth.getCurrentUser();
        if (user == null) {
            start_act dialogFragment = new start_act(this);
            dialogFragment.show(getSupportFragmentManager(), "StartDialog");
        }

        // Load existing image from Firestore
        loadUserProfileImage();

        // Register gallery launcher
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imageView.setImageURI(selectedImageUri);
                        // Show the image
                        Toast.makeText(this, "Image selected please wait " , Toast.LENGTH_SHORT).show();

                        uploadImageToFirebase(selectedImageUri);  // Upload and save to Firestore
                    }
                }
        );

        // When user clicks edit icon
        edit.setOnClickListener(v -> openGallery());
    }

    private void loadUserProfileImage() {
        String uid = myAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document("technician").collection("account").document(uid);

        docRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                String imageUrl = snapshot.getString("image");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(this).load(imageUrl).into(imageView);
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void uploadImageToFirebase(Uri imageUri) {
        String fileName = "profile_" + System.currentTimeMillis() + ".jpg";

        Firebase.upload(this, imageUri, fileName, new Firebase.UploadCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                String uid = myAuth.getCurrentUser().getUid();
                DocumentReference docRef = db.collection("users").document("technician").collection("account").document(uid);

                Map<String, Object> update = new HashMap<>();
                update.put("image", imageUrl);

                docRef.set(update, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(techProfile.this, "Image saved to Firestore.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(techProfile.this, "Failed to save image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(techProfile.this, "Upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(android.view.View view) {
        finish();
    }
}
