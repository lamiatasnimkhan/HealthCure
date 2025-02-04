package com.example.niramoy.registration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.niramoy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextNationalIdPassport = findViewById<EditText>(R.id.editTextNationalIdPassport)
        val checkBoxAgree = findViewById<CheckBox>(R.id.checkBoxAgree)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonNurse = findViewById<Button>(R.id.buttonnurse)
        val buttonLogin = findViewById<Button>(R.id.buttonlogin)

        buttonNurse.setOnClickListener {
            val intent = Intent(this, NurseRegistrationActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val nationalIdPassport = editTextNationalIdPassport.text.toString().trim()
            val isAgree = checkBoxAgree.isChecked

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || nationalIdPassport.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (!isAgree) {
                Toast.makeText(this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show()
            } else {
                // Register user in Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Save additional user data to the database
                            val userId = auth.currentUser?.uid
                            val user = User(
                                userId = userId ?: "",
                                name = name,
                                email = email,
                                nationalIdPassport = nationalIdPassport,
                                role = "patient" // Default role for general users
                            )

                            userId?.let {
                                usersRef.child(it).setValue(user).addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                                        // Redirect to login or another activity
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Database Error: ${dbTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

// Data class to store user information
data class User(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val nationalIdPassport: String = "",
    val role: String = "patient" // Role can be "patient", "nurse", etc.
)
