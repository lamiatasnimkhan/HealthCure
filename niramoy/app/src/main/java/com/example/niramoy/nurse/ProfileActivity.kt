package com.example.niramoy.nurse

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R
import com.example.niramoy.registration.LoginActivity
import com.google.android.material.tabs.TabLayout

class ProfileActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PatientDetailsAdapter

    private lateinit var ivProfilePicture: ImageView
    private lateinit var ivEditIcon: ImageView
    private val PICK_IMAGE_REQUEST = 1

    private val patientReviews = listOf(
        Item.PatientReview("Post-Surgery Care", "20006", "2024-11-01 to 2024-12-30", "Excellent care."),
        Item.PatientReview("Elderly Care", "20025", "2024-11-01 to 2024-12-30", "Very caring."),
        Item.PatientReview("Post-Surgery Care", "20078", "2024-11-01 to 2024-12-30", "Excellent care."),
        Item.PatientReview("Elderly Care", "20090", "2024-11-01 to 2024-12-30", "Very caring."),
        Item.PatientReview("Post-Surgery Care", "30078", "2024-11-01 to 2024-12-30", "Excellent care."),
        Item.PatientReview("Elderly Care", "45022", "2024-11-01 to 2024-12-30", "Very caring."),
    )

    private val blogs = listOf(
        Item.Blog(R.drawable.profile,"Sarah Johnson","BSN","Health Tips", "Stay hydrated and eat balanced meals.We should drink proper water. Water is the most essential thing and obviously for being healthy we have to drink it also with drinking water we have to keep eyes on our food. Some of us have some health issues we have to avoid those food which case issues in our health."),
        Item.Blog(R.drawable.profile,"Sarah johonson","BSN","Daily Routine", "Start your day with meditation.Then walk for 1 hour you can also run with a little speed. Then have your food after that go for your regular work. If you work on laptop whole day then you have to look in the green treas for 1 minute and do it every one hour.If you have pain in your head just close your eyes as give your brain some relaxation and you can also take fresh oxizen.")
    )

    private val certificates = listOf(
        Item.Certificate("CNA", "2023-05-10"),
        Item.Certificate("PALS", "2022-08-15")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)



        val llLogout: LinearLayout = findViewById(R.id.llHistory)

        val llBlog: LinearLayout = findViewById(R.id.llStudyMaterial)

        llLogout.setOnClickListener {
            logoutUser()
        }



        // Set an OnClickListener
        llBlog.setOnClickListener {
            // Navigate to the ProfileActivity
            val intent = Intent(this, Nurse_blog::class.java)
            startActivity(intent)
        }
        val llRequests: LinearLayout = findViewById(R.id.llRequests)

        // Set an OnClickListener
        llRequests.setOnClickListener {
            // Navigate to the ProfileActivity
            val intent = Intent(this, Nurse_homepage::class.java)
            startActivity(intent)
        }

        tabLayout = findViewById(R.id.tabLayout)
        recyclerView = findViewById(R.id.recyclerView)
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        ivEditIcon = findViewById(R.id.ivEditIcon)


        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PatientDetailsAdapter(patientReviews)
        recyclerView.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("Patient Reviews"))
        tabLayout.addTab(tabLayout.newTab().setText("My Blogs"))
        tabLayout.addTab(tabLayout.newTab().setText("My Certificates"))


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> adapter.updateData(patientReviews)
                    1 -> adapter.updateData(blogs)
                    2 -> adapter.updateData(certificates)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        ivEditIcon.setOnClickListener { openImagePicker() }
    }


    private fun logoutUser() {
        // Clear session data if needed (e.g., SharedPreferences, etc.)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Close the current activity
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            ivProfilePicture.setImageURI(imageUri)
        }
    }
}
