package com.example.uni.acts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.uni.R;
import com.example.uni.fragments.ownerLoginAct;
import com.example.uni.helper.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class settingAct extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Uri selectedImageUri;
    private ImageView imageView, edit;
    private EditText editName, editEmail, editPhone, editAddress;
    private TextView profileName;
    private Button btnSaveProfile, btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_act);

        // Initialize UI elements
        imageView = findViewById(R.id.profile_image);
        edit = findViewById(R.id.edit);
        profileName = findViewById(R.id.profile_name);
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
        editAddress = findViewById(R.id.edit_address);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        btnChangePassword = findViewById(R.id.btn_change_password);
        Button logout = findViewById(R.id.logout);

        // Set up click listeners
        logout.setOnClickListener(v -> {
            myAuth.signOut();
            startActivity(new Intent(this, main_act.class));
            finish();
        });

        btnSaveProfile.setOnClickListener(v -> saveProfile());
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        FirebaseUser user = myAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, ownerLoginAct.class));
            finish();
            return;
        }

        // Load existing profile data from Firestore
        loadUserProfileData();
        
        // Register gallery launcher
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imageView.setImageURI(selectedImageUri);  // Show the image
                        Toast.makeText(this, "Image selected, please wait for saving.", Toast.LENGTH_SHORT).show();
                        uploadImageToFirebase(selectedImageUri);  // Upload and save to Firestore
                    }
                }
        );

        // When user clicks edit icon
        edit.setOnClickListener(v -> openGallery());
    }

    private void loadUserProfileData() {
        String uid = myAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document("user").collection("account").document(uid);

        docRef.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                // Load profile image
                String imageUrl = snapshot.getString("image");
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Glide.with(this).load(imageUrl).into(imageView);
                }

                // Load profile data
                String name = snapshot.getString("name");
                String email = snapshot.getString("email");
                String phone = snapshot.getString("phone");
                String address = snapshot.getString("address");

                // Set data to UI
                if (name != null) {
                    profileName.setText(name);
                    editName.setText(name);
                }
                if (email != null) {
                    editEmail.setText(email);
                }
                if (phone != null) {
                    editPhone.setText(phone);
                }
                if (address != null) {
                    editAddress.setText(address);
                }
            } else {
                // If no profile exists, create one with current user email
                FirebaseUser currentUser = myAuth.getCurrentUser();
                if (currentUser != null) {
                    editEmail.setText(currentUser.getEmail());
                }
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to load profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                DocumentReference docRef = db.collection("users").document("user").collection("account").document(uid);

                Map<String, Object> update = new HashMap<>();
                update.put("image", imageUrl);

                docRef.set(update, SetOptions.merge())
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(settingAct.this, "Image saved to Firestore.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(settingAct.this, "Failed to save image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        });
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(settingAct.this, "Upload failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfile() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String address = editAddress.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = myAuth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document("user").collection("account").document(uid);

        Map<String, Object> profileData = new HashMap<>();
        profileData.put("name", name);
        profileData.put("email", email);
        profileData.put("phone", phone);
        profileData.put("address", address);

        docRef.set(profileData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    profileName.setText(name);
                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Password");

        // Create a layout for the dialog
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);

        // Current password field
        EditText currentPasswordInput = new EditText(this);
        currentPasswordInput.setHint("Current Password");
        currentPasswordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(currentPasswordInput);

        // New password field
        EditText newPasswordInput = new EditText(this);
        newPasswordInput.setHint("New Password");
        newPasswordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(newPasswordInput);

        // Confirm new password field
        EditText confirmPasswordInput = new EditText(this);
        confirmPasswordInput.setHint("Confirm New Password");
        confirmPasswordInput.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(confirmPasswordInput);

        builder.setView(layout);

        builder.setPositiveButton("Change Password", (dialog, which) -> {
            String currentPassword = currentPasswordInput.getText().toString().trim();
            String newPassword = newPasswordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "New passwords don't match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newPassword.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(currentPassword, newPassword);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void changePassword(String currentPassword, String newPassword) {
        FirebaseUser user = myAuth.getCurrentUser();
        if (user == null) return;

        // Re-authenticate user with current password
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
        
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Update password
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Failed to update password: " + updateTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void back(android.view.View view) {
        finish();
    }

}
