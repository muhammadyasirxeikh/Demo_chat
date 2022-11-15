package com.example.demo_chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_chat.Model.User

class User_Adapter(private val context: Context, private val userList: ArrayList<User?>) :
    RecyclerView.Adapter<User_Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user= userList[position]
        holder.textView.text = user!!.username
        holder.imageView.setImageResource(R.mipmap.ic_launcher)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MessagingActivty::class.java)
            intent.putExtra("Name", user!!.username)
            intent.putExtra("id", user!!.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var imageView: ImageView

        init {
            textView = itemView.findViewById(R.id.name)
            imageView = itemView.findViewById(R.id.image_id)
        }
    }
}