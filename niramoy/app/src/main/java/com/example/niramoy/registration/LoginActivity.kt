package com.example.niramoy.registration

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.niramoy.R
import com.example.niramoy.nurse.Nurse_homepage
import com.example.niramoy.user.User_Dashboard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailField = findViewById<EditText>(R.id.etEmail)
        val passwordField = findViewById<EditText>(R.id.etPassword)
        val nurseToggle = findViewById<Switch>(R.id.switch1) // Nurse toggle switch
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val registerNowText = findViewById<TextView>(R.id.tvRegisterNow)
        val forgotPasswordText = findViewById<TextView>(R.id.tvForgotPassword)

        // Login Button Click
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val isNurse = nurseToggle.isChecked

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                authenticateUser(email, password, isNurse)
            }
        }

        // "Register Now" Click
        registerNowText.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        // Forgot Password
        forgotPasswordText.setOnClickListener {
            Toast.makeText(this, "Redirecting to Forgot Password Screen", Toast.LENGTH_SHORT).show()
            // Handle forgot password action here
        }
    }

    private fun authenticateUser(email: String, password: String, isNurse: Boolean) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User authenticated successfully
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        if (isNurse) {
                            navigateToNurseDashboard()
                        } else {
                            navigateToPatientDashboard()
                        }
                    } else {
                        Toast.makeText(this, "Failed to fetch user ID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Authentication failed
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToNurseDashboard() {
        val intent = Intent(this, Nurse_homepage::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToPatientDashboard() {
        val intent = Intent(this, User_Dashboard::class.java)
        startActivity(intent)
        finish()
    }
}
