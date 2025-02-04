package com.example.niramoy.nurse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R

sealed class Item {
    data class PatientReview(val details: String,val location: String, val date: String, val review: String) : Item()
    data class Blog(val profile: Int,val name: String, val degree: String, val title: String, val content: String) : Item()
    data class Certificate(val name: String, val issuedBy: String) : Item()
}

class PatientDetailsAdapter(private var itemList: List<Item>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PATIENT_REVIEW = 0
        private const val VIEW_TYPE_BLOG = 1
        private const val VIEW_TYPE_CERTIFICATE = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Item.PatientReview -> VIEW_TYPE_PATIENT_REVIEW
            is Item.Blog -> VIEW_TYPE_BLOG
            is Item.Certificate -> VIEW_TYPE_CERTIFICATE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PATIENT_REVIEW -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.des_review, parent, false)
                PatientReviewViewHolder(view)
            }
            VIEW_TYPE_BLOG -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_blog, parent, false)
                BlogViewHolder(view)
            }
            VIEW_TYPE_CERTIFICATE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.des_certificate, parent, false)
                CertificateViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemList[position]) {
            is Item.PatientReview -> (holder as PatientReviewViewHolder).bind(item)
            is Item.Blog -> (holder as BlogViewHolder).bind(item)
            is Item.Certificate -> (holder as CertificateViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun updateData(newItemList: List<Item>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    class PatientReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPatientCondition: TextView = view.findViewById(R.id.tvJobTitle)
        private val tvPatientLocation: TextView = view.findViewById(R.id.tvServiceCode)
        private val tvWorkDuration: TextView = view.findViewById(R.id.tvWorkDuration)
        private val tvPatientReview: TextView = view.findViewById(R.id.tvPatientReview)

        fun bind(item: Item.PatientReview) {
            tvPatientCondition.text = "Role: ${item.details}"
            tvPatientLocation.text = "Service Code No: ${item.location}"
            tvWorkDuration.text = "Work Duration: ${item.date}"
            tvPatientReview.text = "Review: ${item.review}"
        }
    }

    class BlogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivProfile: ImageView = view.findViewById(R.id.ivProfile)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvDegree: TextView = view.findViewById(R.id.tvDegree)

        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvContent: TextView = view.findViewById(R.id.tvContent)

        fun bind(item: Item.Blog) {
            ivProfile.setImageResource(item.profile)
            tvName.text = item.name
            tvDegree.text = item.degree
            tvTitle.text = item.title
            tvContent.text = item.content
        }
    }

    class CertificateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvCertificateName: TextView = view.findViewById(R.id.tvCertificateName)
        private val tvIssuedBy: TextView = view.findViewById(R.id.tvIssuedBy)

        fun bind(item: Item.Certificate) {
            tvCertificateName.text = item.name
            tvIssuedBy.text = "Issued by: ${item.issuedBy}"
        }
    }
}
