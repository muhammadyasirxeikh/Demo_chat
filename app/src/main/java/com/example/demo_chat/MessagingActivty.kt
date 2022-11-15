package com.example.demo_chat

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_chat.Model.Message_model
import com.example.demo_chat.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class MessagingActivty : AppCompatActivity() {
    var textView: TextView? = null
    var Send: TextView? = null
    var editText: EditText? = null
    var recyclerView: RecyclerView? = null
    var message_adapter: Message_adapter? = null
    var reference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null
    var message_models: MutableList<Message_model?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging_activty)
        recyclerView = findViewById(R.id.recycler_id)
        recyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        recyclerView!!.setLayoutManager(linearLayoutManager)
        textView = findViewById(R.id.username)
        editText = findViewById(R.id.img)
        Send = findViewById(R.id.message)
        val id = intent.getStringExtra("id")
        //
//        String name = getIntent().getStringExtra("Name");
//        textView.setText(name);
        firebaseUser = FirebaseAuth.getInstance().currentUser
        assert(firebaseUser != null)
        assert(id != null)
        reference = FirebaseDatabase.getInstance().getReference("Users").child(id!!)
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(
                    User::class.java
                )
                textView!!.setText(user!!.username)
                readmessage(firebaseUser!!.uid, id)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        //       Toast.makeText(MessagingActivty.this,firebaseUser.getUid()+" - "+id,Toast.LENGTH_LONG).show();
        Send!!.setOnClickListener(View.OnClickListener {
            val message = editText!!.getText().toString()
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(this@MessagingActivty, "Write your message first", Toast.LENGTH_LONG)
                    .show()
            } else {
                sendMessage(FirebaseAuth.getInstance().currentUser!!.uid, id, message)
            }
            editText!!.setText("")
        })
    }

    private fun readmessage(uid: String, id: String?) {
        message_models = ArrayList()
        reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                message_models?.clear()
                for (snapshot in dataSnapshot.children) {
                    val chat = snapshot.getValue(Message_model::class.java)
                    if (chat!!.reciver == uid && chat.send == id ||
                        chat.reciver == id && chat.send == uid
                    ) {
                        message_models!!.add(chat)
                    }
                    message_adapter = Message_adapter(this@MessagingActivty, message_models!!)
                    recyclerView!!.adapter = message_adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun back(view: View?) {
        finish()
    }

    private fun sendMessage(sender: String, reciver: String?, Message: String) {
        val reference = FirebaseDatabase.getInstance().reference
        val hashMap = HashMap<String, Any?>()
        hashMap["send"] = sender
        hashMap["reciver"] = reciver
        hashMap["message"] = Message
        reference.child("Chats").push().setValue(hashMap)
    }
}