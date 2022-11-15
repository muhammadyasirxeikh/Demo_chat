package com.example.demo_chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    var firebaseUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser()
        //check user is login or not
        if (firebaseUser != null) {
            startActivity(Intent(this@MainActivity, Menu::class.java))
        }
    }

    fun login(view: View?) {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }

    fun register(view: View?) {
        startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
    }
}