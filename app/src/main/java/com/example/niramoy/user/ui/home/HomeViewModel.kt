package com.example.niramoy.user.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // LiveData for the list of accepted requests
    private val _acceptedRequests = MutableLiveData<List<AcceptedRequest>>().apply {
        value = listOf(
            AcceptedRequest("Blood Test", "Nurse Alice"),
            AcceptedRequest("X-ray", "Nurse Bob"),
            AcceptedRequest("Routine Check-up", "Nurse Clara")
        )
    }
    val acceptedRequests: LiveData<List<AcceptedRequest>> = _acceptedRequests

    // You can keep the text value if you need it for some reason
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
