package com.example.niramoy.user.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.niramoy.databinding.FragmentRequestBinding
import com.example.niramoy.databinding.ItemNurseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import androidx.recyclerview.widget.RecyclerView

class Request : Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val nurseList = mutableListOf<Nurse>() // List to hold nurse data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout using ViewBinding
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView for nurses
        binding.rvNurses.layoutManager = LinearLayoutManager(requireContext())
        val nurseAdapter = NurseAdapter(nurseList) { nurse ->
            // Handle nurse click to send request
            sendRequestToNurse(nurse)
        }
        binding.rvNurses.adapter = nurseAdapter

        // Fetch nurses from Firebase
        fetchNursesFromFirebase(nurseAdapter)

        // Set up button click listener for creating requests
        binding.btnSaveRequest.setOnClickListener {
            saveRequestToFirebase()
        }

        return root
    }

    private fun fetchNursesFromFirebase(adapter: NurseAdapter) {
        val database = Firebase.database
        val usersRef = database.getReference("users")

        // Listen for changes to users data (where role is "nurse")
        usersRef.orderByChild("role").equalTo("nurse").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nurseList.clear()
                for (userSnapshot in snapshot.children) {
                    val nurse = userSnapshot.getValue(Nurse::class.java)
                    nurse?.let { nurseList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch nurses: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendRequestToNurse(nurse: Nurse) {
        // Get input from the EditText fields for the request
        val title = binding.etRequestTitle.text.toString().trim()
        val description = binding.etRequestDescription.text.toString().trim()
        val fromTime = binding.etFromTime.text.toString().trim()
        val toTime = binding.etToTime.text.toString().trim()
        val numberOfDays = binding.etNumberOfDays.text.toString().trim()
        val salary = binding.etSalary.text.toString().trim()

        // Validate inputs
        if (title.isEmpty() || description.isEmpty() || fromTime.isEmpty() || toTime.isEmpty() || numberOfDays.isEmpty() || salary.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get the current user's UID
        val userId = mAuth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(requireContext(), "User is not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        // Create request data
        val requestData = mapOf(
            "title" to title,
            "description" to description,
            "fromTime" to fromTime,
            "toTime" to toTime,
            "numberOfDays" to numberOfDays,
            "salary" to salary,
            "nurseId" to nurse.id // Store the nurse ID in the request
        )

        // Reference to Firebase Realtime Database under the user's UID
        val database = Firebase.database
        val requestsRef = database.getReference("requests").child(userId)

        // Push the request data to the database under the user's ID
        requestsRef.push().setValue(requestData)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Request sent to nurse successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(requireContext(), "Failed to send request: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveRequestToFirebase() {
        // Logic for saving a request without a nurse selection (fallback scenario)
        Toast.makeText(requireContext(), "Please select a nurse to send a request", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Data class for Nurse
data class Nurse(
    val id: String = "",
    val name: String = "",
    val specialty: String = "",
    val imageUrl: String = "" // Add image URL if available
)

// RecyclerView Adapter for Nurses
class NurseAdapter(private val nurseList: List<Nurse>, private val onClick: (Nurse) -> Unit) : RecyclerView.Adapter<NurseAdapter.NurseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NurseViewHolder {
        val binding = ItemNurseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NurseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NurseViewHolder, position: Int) {
        val nurse = nurseList[position]
        holder.bind(nurse, onClick)
    }

    override fun getItemCount(): Int = nurseList.size

    inner class NurseViewHolder(private val binding: ItemNurseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nurse: Nurse, onClick: (Nurse) -> Unit) {
            binding.tvNurseName.text = nurse.name
            binding.tvNurseSpecialty.text = nurse.specialty
            // You can set an image from the nurse's imageUrl if needed
            binding.btnSendRequest.setOnClickListener {
                onClick(nurse)
            }
        }
    }
}
