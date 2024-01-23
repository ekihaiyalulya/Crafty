package com.example.crafty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val arrow_left: ImageButton = findViewById(R.id.arrow_left)

        arrow_left.setOnClickListener {
            startActivity(Intent(this@DetailProductActivity, MainActivity::class.java))
        }
    }
}