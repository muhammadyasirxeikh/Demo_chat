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
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {
    var email: EditText? = null
    var pass: EditText? = null
    var username: EditText? = null
    var button: Button? = null
    var auth: FirebaseAuth? = null
    var reference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById(R.id.email)
        pass = findViewById(R.id.password)
        button = findViewById(R.id.button)
        username = findViewById(R.id.user)
        auth = FirebaseAuth.getInstance()
        button?.setOnClickListener(View.OnClickListener {
            val Email = email?.text.toString()
            val Username = username?.text.toString()
            val Password = pass?.text.toString()
            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(Username) || TextUtils.isEmpty(
                    Password
                )
            ) {
                Toast.makeText(this@RegisterActivity, "Data is necceasry", Toast.LENGTH_LONG).show()
            } else if (Password.length < 6) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password must be 6 characters",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                register(Email, Username, Password)
            }
        })
    }

    private fun register(email: String, username: String, password: String) {
        val loading: AlertDialog = ProgressDialog(this@RegisterActivity)
        loading.setMessage("Loading...")
        loading.show()
        auth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth!!.currentUser!!
                    val userid = firebaseUser.uid
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid)
                    val hashMap = HashMap<String, String>()
                    hashMap["id"] = userid
                    hashMap["username"] = username
                    hashMap["email"] = email
                    hashMap["password"] = password
                    hashMap["imageURL"] =
                        "https://upload.wikimedia.org/wikipedia/commons/7/7c/User_font_awesome.svg"
                    reference!!.setValue(hashMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            loading.dismiss()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                } else {
                    loading.dismiss()
                    Toast.makeText(this@RegisterActivity, "Invalid Data", Toast.LENGTH_LONG).show()
                }
            }
    }
}