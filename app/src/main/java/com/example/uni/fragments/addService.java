package com.example.uni.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.uni.entities.Service;
import com.example.uni.helper.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addService extends DialogFragment {

    private  ActivityResultLauncher<Intent> galleryLauncher;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Uri selectedImageUri;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addservice_dialog, container, false);

        EditText nameEditText = view.findViewById(R.id.editTextName);
        EditText priceEditText = view.findViewById(R.id.editTextPrice);
        EditText description = view.findViewById(R.id.editText3);
        imageView = view.findViewById(R.id.imageViewSelected);
        Button submitButton = view.findViewById(R.id.btnSubmit);

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        imageView.setImageURI(selectedImageUri);
                    }
                }
        );

        imageView.setOnClickListener(v -> openGallery());

        submitButton.setOnClickListener(v -> {
            String price = priceEditText.getText().toString().trim();
            String name = nameEditText.getText().toString().trim();
            String des = description.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty() || des.isEmpty() || selectedImageUri == null) {
                Toast.makeText(getContext(), "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            } else {
                // Generate a unique filename for the image (e.g., using the current timestamp)
                String fileName = "service_" + System.currentTimeMillis() + ".jpg";

                // Call the Firebase.upload method
                Firebase.upload(getContext(), selectedImageUri, fileName, new Firebase.UploadCallback() {
                    @Override
                    public void onSuccess(String imageUrl) {
                        // Create a ServiceType object
                        double numPrice = Double.parseDouble(price);
                        Service service = new Service(numPrice,imageUrl, name,des);

                        // Prepare data to be stored in Firestore
                        Map<String, Object> data = new HashMap<>();
                        data.put("uid", service.getId());
                        data.put("name", service.getName());
                        data.put("Description", service.getDescription());
                        data.put("image", service.getImage());
                        data.put("price",service.getPrice());
                        DocumentReference reference = db.collection("serviceType").document("Grooming");
                        reference.get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                CollectionReference document = reference.collection("package");
                                document.add(data).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(getContext(), "Service added: " + service.getName(), Toast.LENGTH_SHORT).show();
                                        dismiss();
                                    }
                                });
                            }
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
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


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

}

