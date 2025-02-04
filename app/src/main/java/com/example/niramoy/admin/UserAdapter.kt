package com.example.niramoy.admin



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val certTextView: TextView = itemView.findViewById(R.id.certTextView)
        val fieldTextView: TextView = itemView.findViewById(R.id.fieldTextView)
        val verifyButton: Button = itemView.findViewById(R.id.verifyButton)
        val blockButton: Button = itemView.findViewById(R.id.blockButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.nameTextView.text = "Name: ${user.name}"
        holder.idTextView.text = "Nationa ID: ${user.nationalId}"
        holder.certTextView.text = "Licence No: ${user.certificate}"
        holder.fieldTextView.text = "Field: ${user.specialField}"

        holder.verifyButton.setOnClickListener {
            // Implement verification logic here
            Toast.makeText(it.context, "${user.name} Verified!", Toast.LENGTH_SHORT).show()
        }

        holder.blockButton.setOnClickListener {
            // Implement blocking logic here
            Toast.makeText(it.context, "${user.name} Blocked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = userList.size
}
