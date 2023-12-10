package com.example.crafty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@SplashActivity, RegisterActivity::class.java))
        }
    }
}