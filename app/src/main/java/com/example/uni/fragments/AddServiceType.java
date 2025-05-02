package com.example.uni.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.ServiceType;
import com.example.uni.helper.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddServiceType extends DialogFragment {

    private ActivityResultLauncher<Intent> galleryLauncher;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Uri selectedImageUri;
    private ImageView imageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addservice_type, container, false);

        EditText nameEditText = view.findViewById(R.id.TextName);
        EditText priceEdit = view.findViewById(R.id.editText3);
        imageView = view.findViewById(R.id.imageView);
        Button submitButton = view.findViewById(R.id.confirm);
        Button cancelButton = view.findViewById(R.id.cancel);

        // Register for result from gallery selection
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imageView.setImageURI(selectedImageUri);  // Show the selected image in ImageView
                    }
                }
        );

        imageView.setOnClickListener(v -> openGallery());

        cancelButton.setOnClickListener(v -> {
                    requireActivity().recreate();
                    dismiss();
                });

        submitButton.setOnClickListener(v -> {
            String price = priceEdit.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || selectedImageUri == null) {
                Toast.makeText(getContext(), "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            } else {
                // Generate a unique filename for the image (e.g., using the current timestamp)
                String fileName = "service_" + System.currentTimeMillis() + ".jpg";

                // Call the Firebase.upload method
                Firebase.upload(getContext(), selectedImageUri, fileName, new Firebase.UploadCallback() {
                    @Override
                    public void onSuccess(String imageUrl) {
                        // Create a ServiceType object
                        ServiceType service = new ServiceType(price, imageUrl, name);

                        // Prepare data to be stored in Firestore
                        Map<String, Object> data = new HashMap<>();
                        data.put("uid", service.getId());
                        data.put("name", service.getName());
                        data.put("Description", service.getDescription());
                        data.put("image", service.getImage());

                        // Upload data to Firestore
                        db.collection("serviceType").document(service.getName()).set(data)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getContext(), "Service added: " + service.getName(), Toast.LENGTH_SHORT).show();
                                    requireActivity().recreate();
                                    dismiss();  // Close the dialog
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    // Method to open the gallery and select an image
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);  // Open gallery for image selection
    }

}
