package com.example.crafty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        RelativeLayout btnDone = findViewById(R.id.buttonDone);

        btnDone.setOnClickListener(view -> {
            startActivity(new Intent(SuccessActivity.this, MainActivity.class));
        });
    }
}