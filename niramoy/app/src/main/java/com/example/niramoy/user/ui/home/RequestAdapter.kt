package com.example.niramoy.user.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.databinding.ItemAcceptedRequestBinding


data class AcceptedRequest(val requestName: String, val nurseName: String)

class RequestAdapter(private val requests: List<AcceptedRequest>) :
    RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemAcceptedRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.bind(request)
    }

    override fun getItemCount(): Int = requests.size

    inner class RequestViewHolder(private val binding: ItemAcceptedRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(request: AcceptedRequest) {
            binding.tvRequestName.text = request.requestName
            binding.tvNurseName.text = "Accepted by: ${request.nurseName}"
        }
    }
}
