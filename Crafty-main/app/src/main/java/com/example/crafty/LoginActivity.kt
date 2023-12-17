package com.example.crafty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin1: Button = findViewById(R.id.btnLogin1)

        btnLogin1.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

    }
}