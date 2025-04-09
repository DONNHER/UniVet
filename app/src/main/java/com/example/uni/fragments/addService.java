package com.example.uni.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import androidx.compose.ui.text.font.FontVariation;
import androidx.fragment.app.DialogFragment;

import com.example.uni.R;
import com.example.uni.entities.Service;
import com.example.uni.helper.TempStorage;

public class addService extends DialogFragment {


    private static final TempStorage temp = TempStorage.getInstance();
    private  ActivityResultLauncher<Intent> galleryLauncher;


    private Uri selectedImageUri;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addservice_dialog, container, false);

        EditText nameEditText = view.findViewById(R.id.editTextName);
        EditText priceEditText = view.findViewById(R.id.editTextPrice);
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
            double price =Double.parseDouble( priceEditText.getText().toString().trim());
            String name = nameEditText.getText().toString().trim();

            if (nameEditText.getText().toString().isEmpty() || priceEditText.getText().toString().isEmpty() || selectedImageUri == null) {
                Toast.makeText(getContext(), "Please fill all fields and select an image.", Toast.LENGTH_SHORT).show();
            } else {
                // You can return data to parent or store it
                temp.addNService(new Service( price, selectedImageUri.toString(),name));
                Toast.makeText(getContext(), "Item added: " + name + " ₱" + price, Toast.LENGTH_SHORT).show();
                dismiss();
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

