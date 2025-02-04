package com.example.niramoy.user
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R


data class Nurse(
    val name: String = "",
    val specialty: String = "",
    val id: String = "" // this will be the UID of the nurse
)

class NurseAdapter(
    private val nurses: List<Nurse>,
    private val onClick: (Nurse) -> Unit
) : RecyclerView.Adapter<NurseAdapter.NurseViewHolder>() {

    inner class NurseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvNurseName)
        val specialty: TextView = itemView.findViewById(R.id.tvNurseSpecialty)

        init {
            itemView.setOnClickListener {
                onClick(nurses[adapterPosition]) // pass the clicked nurse to the onClick function
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NurseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nurse, parent, false)
        return NurseViewHolder(view)
    }

    override fun onBindViewHolder(holder: NurseViewHolder, position: Int) {
        val nurse = nurses[position]
        holder.name.text = nurse.name
        holder.specialty.text = nurse.specialty
    }

    override fun getItemCount(): Int = nurses.size
}
