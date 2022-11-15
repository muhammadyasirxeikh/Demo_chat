package com.example.demo_chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_chat.Model.Message_model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Message_adapter(val  context: Context, val userList: List<Message_model?>) : RecyclerView.Adapter<Message_adapter.ViewHolder>() {
    val Type_Left = 0
    val Type_Right = 1


    var firebaseUser: FirebaseUser? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == Type_Right) {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  message_model= userList[position]
        holder.textView.text = message_model?.message
    }

    override fun getItemCount(): Int {
        return userList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView

        init {
            textView = itemView.findViewById(R.id.message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        return if (userList!![position]!!.send == firebaseUser!!.uid) {
            Type_Right
        } else {
            Type_Left
        }
    }
}