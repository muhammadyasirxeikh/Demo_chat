package com.example.demo_chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_chat.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class User_fragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var userList: ArrayList<User?>? = null
    var user_adapter: User_Adapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_id)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        userList = ArrayList()
        readUser()
        return view
    }

    private fun readUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList!!.clear()
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(
                        User::class.java
                    )
                    assert(firebaseUser != null)
                    assert(user != null)
                    if (user!!.id == firebaseUser!!.uid) {
                    } else {
                        userList!!.add(user)
                    }
                }
                user_adapter = User_Adapter(context!!, userList!!)
                recyclerView!!.adapter = user_adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}