package com.example.crafty

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.crafty.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionbar
//        actionBar = supportActionBar!!
//        actionBar.title ="Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //handle click, begin login
        binding.btnLogin1.setOnClickListener{
            //before logging in, validate data
            validateData()
        }
        //handle click, open signupActivity
        binding.Register1.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun validateData() {
        //get data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid Email Format"
        }
        else if (TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error="Please enter your Password"
        }
        else{
            //data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                //login Sucess
                progressDialog.dismiss()
                //get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged $email", Toast.LENGTH_SHORT).show()
                finish()

                //open profile
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to $(e.message)", Toast.LENGTH_SHORT).show()
            }
    }


    private fun checkUser() {
        // if user already logged in go to profile activity
        //get current bar
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user is already logged in

        }
    }

}