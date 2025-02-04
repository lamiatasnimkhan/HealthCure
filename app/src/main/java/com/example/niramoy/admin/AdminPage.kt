package com.example.niramoy.admin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R

class AdminPage : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_admin_page)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1) // Two columns in GridLayout

        // Sample data
        val userList = listOf(
            User("John Doe", "123456789", "Cert001", "Cardiology"),
            User("Jane Smith", "987654321", "Cert002", "Pediatrics"),
            User("Alice Johnson", "456789123", "Cert003", "Orthopedics"),
            User("Bob Brown", "321654987", "Cert004", "Neurology")
        )

        val adapter = UserAdapter(userList)
        recyclerView.adapter = adapter

    }
}