package com.example.crafty;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class EditProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        EditText editTextName = findViewById(R.id.aadd_product);
        EditText editTextPrice = findViewById(R.id.aadd_hargaa_product);
        EditText editTextDesk = findViewById(R.id.aadd_deskripsi);
        ImageView imageViewProduct = findViewById(R.id.uploadImageProduct);
        FloatingActionButton fabEditProduct = findViewById(R.id.buttoneditproduct);

        // Retrieve the data from the intent
        DataClass2 selectedItem = getIntent().getParcelableExtra("selectedItem");

        // Populate the UI elements with the data
        if (selectedItem != null) {
            editTextName.setText(selectedItem.getCaption1());
            editTextPrice.setText(selectedItem.getCaption2());
            editTextDesk.setText(selectedItem.getCaption3());

            // You can use a library like Glide to load the image into the ImageView
            Glide.with(this).load(selectedItem.getImageUrl()).into(imageViewProduct);
        }

        // Set click listener to the ImageView for choosing a new image
        imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        // Add click listener to the edit button
        fabEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement the update logic here
                // You need to retrieve the edited data from the UI elements and update it in Firebase
                // After updating, you can navigate back to the DetailGridActivity or any other desired activity

                // Retrieve edited data from UI elements
                String editedName = editTextName.getText().toString().trim();
                String editedPrice = editTextPrice.getText().toString().trim();
                String editedDesk = editTextDesk.getText().toString().trim();

                // If the user has chosen a new image, selectedImageUri will not be null
                if (selectedImageUri != null) {
                    // Upload the new image to Firebase Storage
                    // Implement the image upload logic here and obtain the download URL
                    // For example, you can use Firebase Storage to upload the image
                    // Update the ImageView with the new image URL
                }

                // Update the data in Firebase Realtime Database
                updateProductInFirebase(selectedItem.getKey(), editedName, editedPrice, editedDesk);
            }
        });
    }

    private void openImagePicker() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Load the selected image into the ImageView using Glide or your preferred image loading library
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ImageView imageView = findViewById(R.id.uploadImageProduct);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to update the data in Firebase Realtime Database
    private void updateProductInFirebase(String key, String editedName, String editedPrice, String editedDesk) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product").child(key);
        DataClass2 selectedItem = getIntent().getParcelableExtra("selectedItem");

        // Create a new DataClass2 object with the edited data
        DataClass2 updatedProduct = new DataClass2();
        updatedProduct.setCaption1(editedName);
        updatedProduct.setCaption2(editedPrice);
        updatedProduct.setCaption3(editedDesk);

        // If a new image is chosen, set the updated image URL
        if (selectedImageUri != null) {
            // Implement the logic to upload the new image to Firebase Storage
            String imageName = key + ".jpg"; // Use a unique name for the image file
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("product_images/" + imageName);

            storageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Image upload success
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String newImageUrl = uri.toString();
                            updatedProduct.setImageUrl(newImageUrl);

                            // Update the data in Firebase Realtime Database
                            productRef.setValue(updatedProduct);

                            // Provide feedback to the user (optional)
                            // You can show a Toast or use other UI elements to indicate that the update was successful

                            // Navigate back to the DetailGridActivity or any other desired activity
                            startActivity(new Intent(EditProductActivity.this, ProductActivity.class));
                            finish();
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Image upload failed
                        e.printStackTrace();
                        // Handle the failure (show a Toast, log the error, etc.)
                    });
        } else {
            // If no new image is chosen, keep the existing image URL
            updatedProduct.setImageUrl(selectedItem.getImageUrl());

            // Update the data in Firebase Realtime Database
            productRef.setValue(updatedProduct);

            // Provide feedback to the user (optional)
            // You can show a Toast or use other UI elements to indicate that the update was successful

            // Navigate back to the DetailGridActivity or any other desired activity
            startActivity(new Intent(EditProductActivity.this, ProductActivity.class));
            finish();
        }
    }

}
