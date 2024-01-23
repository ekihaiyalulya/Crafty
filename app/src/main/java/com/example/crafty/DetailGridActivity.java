package com.example.crafty;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_grid);

        ImageButton arrow_left = findViewById(R.id.arrow_left);

        RelativeLayout btnChat = findViewById(R.id.buttonchat);
        RelativeLayout btnOrder = findViewById(R.id.buttonContainer);

        RelativeLayout btnUpdate = findViewById(R.id.buttonupdate);
        TextView textUpdate = findViewById(R.id.textupdate);
        ImageView imgUpdate =  findViewById(R.id.imageupdate);

        RelativeLayout btnDelete = findViewById(R.id.buttondelete);
        TextView textDelete = findViewById(R.id.textdelete);
        ImageView imgDelete =  findViewById(R.id.imagedelete);

        arrow_left.setOnClickListener(view -> {
            startActivity(new Intent(DetailGridActivity.this, ProductActivity.class));
        });

        DataClass2 selectedItem = getIntent().getParcelableExtra("selectedItem");
        TextView nameTextView = findViewById(R.id.name_product);
        nameTextView.setText(selectedItem != null ? selectedItem.getCaption1() : null);
        TextView nameTextView2 = findViewById(R.id.harga_product);
        nameTextView2.setText(selectedItem != null ? selectedItem.getCaption2() : null);
        TextView nameTextView3 = findViewById(R.id.caption3);
        nameTextView3.setText(selectedItem != null ? selectedItem.getCaption3() : null);

        ImageButton imageProd = findViewById(R.id.produk);
        if (selectedItem != null) {
            String imageUrl = selectedItem.getImageUrl();
            if (imageUrl != null) {
                Glide.with(this)
                        .load(imageUrl)
                        .into(imageProd);
            }
        }

        btnOrder.setOnClickListener(view -> {
            Intent cartIntent = new Intent(DetailGridActivity.this, CartActivity.class);
            cartIntent.putExtra("selectedProduct", selectedItem);
            startActivity(cartIntent);
        });

        // Add click listener to the delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the selected item is not null
                if (selectedItem != null) {
                    // Retrieve the key of the selected item
                    String selectedKey = selectedItem.getKey();

                    // Remove the item from Firebase Realtime Database
                    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Product").child(selectedKey);
                    productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Provide feedback to the user (optional)
                                Toast.makeText(DetailGridActivity.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();

                                // Navigate back to ProductActivity (or any other desired activity)
                                startActivity(new Intent(DetailGridActivity.this, ProductActivity.class));
                                finish();
                            } else {
                                // Handle the case where deletion failed
                                Toast.makeText(DetailGridActivity.this, "Failed to delete product", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the selected item is not null
                if (selectedItem != null) {
                    // Create an Intent to start EditProductActivity
                    Intent intent = new Intent(DetailGridActivity.this, EditProductActivity.class);

                    // Attach the data of the selected item to the intent
                    intent.putExtra("selectedItem", selectedItem);

                    // Start EditProductActivity
                    startActivity(intent);
                }
            }
        });


        // Gantilah 'admin@gmail.com' dengan identifier sesuai dengan data pengguna yang login
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // Pemeriksaan untuk menentukan visibilitas tombol
        if (currentUser != null && currentUser.getEmail().equals("admin@gmail.com")) {
            // Jika pengguna adalah admin, tampilkan tombol Update dan Delete, sembunyikan tombol Chat dan Order
            btnUpdate.setVisibility(View.VISIBLE);
            textUpdate.setVisibility(View.VISIBLE);
            imgUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            textDelete.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            btnChat.setVisibility(View.GONE);
            btnOrder.setVisibility(View.GONE);
        } else {
            // Jika pengguna bukan admin, sembunyikan tombol Update dan Delete, tampilkan tombol Chat dan Order
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnChat.setVisibility(View.VISIBLE);
            btnOrder.setVisibility(View.VISIBLE);
        }
    }
}