package com.example.niramoy.nurse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R

class RequestAdapter(
    private val requestList: List<Request>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Define view types
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recycler_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_request, parent, false)
            RequestViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RequestViewHolder) {
            // Bind item data (adjusting for the header at position 0)
            holder.setData(requestList[position - 1])
        }
    }

    override fun getItemCount(): Int {
        // Include the header in the count
        return requestList.size + 1
    }

    // ViewHolder for the header
    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Initialize header views here if needed
    }

    // ViewHolder for the list items
    class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPatientName: TextView = view.findViewById(R.id.tvPatientName)
        private val tvRequestDetails: TextView = view.findViewById(R.id.tvRequestDetails)
        private val tvRequestDate: TextView = view.findViewById(R.id.tvRequestDate)
        private val tvTimePeriod: TextView = view.findViewById(R.id.tvTimePeriod)
        private val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        private val tvSalary: TextView = view.findViewById(R.id.tvSalary)
        private val ivProfilePicture: ImageView = view.findViewById(R.id.ivProfilePicture)

        fun setData(request: Request) {
            tvPatientName.text = "Patient Name: ${request.name}"
            tvRequestDetails.text = "Care Needed: ${request.details}"
            tvRequestDate.text = "Service Code No: ${request.duration}"
            tvTimePeriod.text = "Time Range: ${request.period}"
            tvLocation.text = "Location: ${request.location}"
            tvSalary.text = "Salary: ${request.salary}"
            ivProfilePicture.setImageResource(request.profileImageResId)
        }
    }
}
