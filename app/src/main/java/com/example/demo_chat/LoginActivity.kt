package com.example.demo_chat

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {
    var button: Button? = null
    var email: EditText? = null
    var pass:EditText? = null

    var auth: FirebaseAuth? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        button = findViewById(R.id.login)
        email = findViewById(R.id.email)
        pass = findViewById<EditText>(R.id.password)
        auth = FirebaseAuth.getInstance()
        button?.setOnClickListener(View.OnClickListener {
            val Email = email!!.text.toString()
            val Password: String = pass!!.text.toString()
            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Password)) {
                Toast.makeText(this@LoginActivity, "Data is necessary", Toast.LENGTH_LONG).show()
            } else if (Password.length < 6) {
                Toast.makeText(
                    this@LoginActivity,
                    "Password must be 6 characters",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                login(Email, Password)
            }
        })
    }

    private fun login(email: String, password: String) {
        val loading: AlertDialog = ProgressDialog(this@LoginActivity)
        loading.setMessage("Loading...")
        loading.show()
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loading.dismiss()
                    val intent = Intent(this@LoginActivity, Menu::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    loading.dismiss()
                    Toast.makeText(this@LoginActivity, "Aisa to ni hota", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}