package com.example.toolbar


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button

import android.widget.TextView
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var myAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        myAuth = FirebaseAuth.getInstance()
        val signupbutton = findViewById<Button>(R.id.btnSignUp)
        //val userId = findViewById<TextInputEditText>(R.id.etUserId)
        val name = findViewById<TextInputEditText>(R.id.etName)
        val etmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etpassword = findViewById<TextInputEditText>(R.id.etPass)
        val navToSignIn = findViewById<TextView>(R.id.navToSignIn)

        navToSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        signupbutton.setOnClickListener {

            val etName = name.text.toString()
            val email = etmail.text.toString()
            val password = etpassword.text.toString()
            signUp(etName,email,password)

        }
    }
    private fun addToDatabase(name:String,email: String,password: String,uid:String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        println(databaseReference)
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            val databaseChildReference = databaseReference.child(uid)
            val user = User(name, email, password,uid)
            databaseChildReference.setValue(user).addOnSuccessListener {

               /* name.text?.clear()
                etmail.text?.clear()
                etpassword.text?.clear()*/
                Log.d("Database", "Stored inside the database ok!!")
            }.addOnFailureListener {
                Toast.makeText(this, "User Not Registered", Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this,"Fields can't be empty",Toast.LENGTH_SHORT).show()
        }

    }


    private fun signUp(name : String,email: String, password: String) {
        myAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)

                    addToDatabase(name,email,password,myAuth.uid!!)
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.updateProfile(buildProfileChangeRequest(name))
                    intent.putExtra("name",name)
                    intent.putExtra("email",email)

                    startActivity(intent)
                    Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                  Toast.makeText(this@LoginActivity,"Account Already Exists",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    // Update Display Name
    fun updateDisplayName(displayName: String) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.updateProfile(buildProfileChangeRequest(displayName))
    }

    // Helper function to create profile change request
    private fun buildProfileChangeRequest(displayName: String) =
        UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
}
