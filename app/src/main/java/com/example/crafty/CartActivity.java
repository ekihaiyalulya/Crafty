package com.example.crafty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ImageButton up;
    ImageButton down;
    int count = 0;

    TextView number;
    TextView numberTot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        number = findViewById(R.id.jumlah_pro);
        numberTot = findViewById(R.id.total_jum);
        up.setOnClickListener(this::onClick);
        down.setOnClickListener(this::onClick);

        // Retrieve the selected product information from the Intent
        DataClass2 selectedProduct = getIntent().getParcelableExtra("selectedProduct");

        // Update the UI with the selected product information
        if (selectedProduct != null) {
            ImageView proImage = findViewById(R.id.pro_img);
            TextView productName = findViewById(R.id.nama_pro);
            TextView productPrice = findViewById(R.id.harga_pro);

            TextView productPriceSub = findViewById(R.id.sub_value);
            TextView productPriceTot = findViewById(R.id.total_value);

            // Set the product image, name, and price
            Glide.with(this).load(selectedProduct.getImageUrl()).into(proImage);
            productName.setText(selectedProduct.getCaption1());
            productPrice.setText(selectedProduct.getCaption2());

            productPriceSub.setText(selectedProduct.getCaption2());
            productPriceTot.setText(selectedProduct.getCaption2());
        }

        RelativeLayout btnOrder = findViewById(R.id.buttonContainer);
        ImageButton btnTrash = findViewById(R.id.ikon_trash);

        btnOrder.setOnClickListener(view -> {
            Toast.makeText(CartActivity.this, "Produk berhasil dipesan", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CartActivity.this, SuccessActivity.class));
        });

        btnTrash.setOnClickListener(view -> {
            Toast.makeText(CartActivity.this, "Pesanan berhasil dihapus", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CartActivity.this, ProductActivity.class));
        });
    }

    public void onClick(View view){
        if (view.getId() == R.id.up){
            count = count + 1;
            setText();
        } else if (view.getId() == R.id.down) {
            if (count > 0) {
                count = count - 1;
                setText();
            }
        }
    }
    public void setText(){
        number.setText(Integer.toString(count));
        numberTot.setText(Integer.toString(count));
    }

}
