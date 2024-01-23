package com.example.crafty

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val dashboard_button: ConstraintLayout = findViewById(R.id.text_dashboard)

        dashboard_button.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, DashboardActivity::class.java))
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null && currentUser.email == "admin@gmail.com") {
            // User is logged in as admin, show fab2
            dashboard_button.setVisibility(View.VISIBLE)
        } else {
            // User is not logged in or not admin, hide fab2
            dashboard_button.setVisibility(View.GONE)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val itemId = item.itemId
            if (itemId == R.id.navigation_home) {
                val profileIntent = Intent(
                    this@ProfileActivity,
                    MainActivity::class.java
                )
                startActivity(profileIntent)
                return@OnNavigationItemSelectedListener true
            } else if (itemId == R.id.navigation_chart) { // Handle Chart button click
                val profileIntent = Intent(
                    this@ProfileActivity,
                    ProductActivity::class.java
                )
                startActivity(profileIntent)
                return@OnNavigationItemSelectedListener true
            } else if (itemId == R.id.navigation_profile) { // Handle Profile button click
                val profileIntent = Intent(
                    this@ProfileActivity,
                    ProfileActivity::class.java
                )
                startActivity(profileIntent)
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }
}