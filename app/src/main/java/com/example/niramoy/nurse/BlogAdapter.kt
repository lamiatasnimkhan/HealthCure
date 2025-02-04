package com.example.niramoy.nurse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.niramoy.R


data class Blog(
    val name: String,
    val profileImage: Int,
    val degree: String,
    val title: String,
    val content: String
)


class BlogAdapter(private val blogs: List<Blog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.ivProfile)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val degree: TextView = itemView.findViewById(R.id.tvDegree)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val content: TextView = itemView.findViewById(R.id.tvContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogs[position]
        holder.profileImage.setImageResource(blog.profileImage)
        holder.name.text = blog.name
        holder.degree.text = blog.degree
        holder.title.text = blog.title
        holder.content.text = blog.content
    }

    override fun getItemCount(): Int = blogs.size
}