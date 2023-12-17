package com.example.crafty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.crafty.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tas1: ImageButton = findViewById(R.id.tas1)

        tas1.setOnClickListener {
            startActivity(Intent(this@HomeActivity, DetailProductActivity::class.java))
        }
    }
}