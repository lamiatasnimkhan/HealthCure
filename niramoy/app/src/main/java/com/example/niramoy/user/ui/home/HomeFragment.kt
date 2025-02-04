package com.example.niramoy.user.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.niramoy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var requestAdapter: RequestAdapter

    private val acceptedRequests = listOf(
        AcceptedRequest("Postpartum Care", "Nurse Nipa"),
        AcceptedRequest("Jaundice Routine Care", "Nurse Rahma"),
        //("Routine Check-up", "Nurse Clara")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up the RecyclerView with the adapter and layout manager
        requestAdapter = RequestAdapter(acceptedRequests)
        binding.rvAcceptedRequests.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAcceptedRequests.adapter = requestAdapter

        return binding.root
    }
}
