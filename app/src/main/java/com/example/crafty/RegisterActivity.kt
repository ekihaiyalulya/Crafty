package com.example.crafty

import android.app.ProgressDialog
import android.content.Intent
import android.health.connect.datatypes.units.Length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.crafty.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityRegisterBinding

    //ActionBar
//    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configre progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Creating account...")
        progressDialog.setCancelable(false)


        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //handle, click, begin signup
        binding.btnRegist.setOnClickListener{
            validateData()
        }
        binding.haveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun validateData(){
        //get data
        email= binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            //password isnt entered
            binding.passwordEt.error = "Please enter Password"
        }
        else if (password.length <8){
            //password length is less than 8
            binding.passwordEt.error = "Password mush atleast 8 characters long"
        }
        else{
            //data is valid,contonue ssignup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()

        //create Account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                //get current user
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val userEmail = firebaseUser.email
                    Toast.makeText(this, "Account Created with email $email", LENGTH_SHORT).show()
                }
                //open profile
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener{ e->
                //signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "SignUp failed due to ${e.message}", LENGTH_SHORT).show()

            }
    }
}
