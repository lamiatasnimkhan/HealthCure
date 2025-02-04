package com.example.niramoy.nurse

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R
import com.example.niramoy.databinding.ActivityNurseBlogBinding


class Nurse_blog : AppCompatActivity() {




    private lateinit var binding: ActivityNurseBlogBinding
    private val blogList = mutableListOf<Blog>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nurse_blog)







        binding = ActivityNurseBlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llRequests.setOnClickListener{
            val intent = Intent(this, Nurse_homepage::class.java)
            startActivity(intent)
        }
        binding.llProfile.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Setup RecyclerView
        val blogAdapter = BlogAdapter(blogList)
        binding.recyclerViewBlogs.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewBlogs.adapter = blogAdapter

        // Post Button Click
        binding.btnPost.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                val newBlog = Blog(
                    name = "Nurse Jane Doe",
                    profileImage = R.drawable.profile, // Replace with your drawable resource
                    degree = "RN, BSN",
                    title = title,
                    content = content
                )
                blogList.add(0, newBlog)
                blogAdapter.notifyItemInserted(0)
                binding.recyclerViewBlogs.scrollToPosition(0)

                binding.etTitle.text.clear()
                binding.etContent.text.clear()
                Toast.makeText(this, "Blog Posted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }



        blogList.add(
            Blog(
                name = "Nurse Emily",
                profileImage = R.drawable.pro, // Add this drawable
                degree = "RN, MSN",
                title = "The Art of Compassionate Nursing",
                content = "Nursing is not just a profession; it's an art of caring, compassion, and dedication. Over my 10 years in this field, I have learned that every patient has a story that goes beyond their medical charts. Each interaction is an opportunity to provide comfort, support, and hope. From assisting in critical surgeries to offering a listening ear during tough times, every moment has enriched my journey. Nursing teaches you resilience, empathy, and the power of a kind word. The more I learn, the more I realize that nursing is not just a career; it’s a calling."
            )

        )
        blogList.add(
            Blog(
                name = "Nurse Bob",
                profileImage = R.drawable.pro, // Add this drawable
                degree = "RN, BSN",
                title = "My Journey in Nursing",
                content = "Nursing has been a fulfilling career for me. I am working as a nurse for 8 years still i am learning and bla bla.."
            )
        )

        blogList.add(
            Blog(
                name = "Nurse sob",
                profileImage = R.drawable.pro, // Add this drawable
                degree = "RN, BSN",
                title = "Nursing",
                content = "Nursing has been a fulfilling career for me. I am working as a nurse for 8 years still i am learning and bla bla.."
            )
        )









    }
}