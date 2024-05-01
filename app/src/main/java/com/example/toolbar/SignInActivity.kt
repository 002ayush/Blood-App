package com.example.toolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInActivity : AppCompatActivity() {
   // lateinit var databaseReference: DatabaseReference
    lateinit var myAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val signInbtn = findViewById<Button>(R.id.btnSignIn)
        val userId = findViewById<TextInputEditText>(R.id.signinuser)
        val password = findViewById<TextInputEditText>(R.id.signpass)

        myAuth = FirebaseAuth.getInstance()
        signInbtn.setOnClickListener {
            val userEmail = userId.text.toString()
            val passwordValue = password.text.toString()
            if (userEmail.isNotEmpty() && passwordValue.isNotEmpty()) {
                login(userEmail, passwordValue)
            } else {

                userId.error = "User Id is required!!!!"

            }
        }
    }

    private fun login(email: String, password: String) {
        myAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    val intent = Intent(this@SignInActivity, MainActivity::class.java)
                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    val username = FirebaseAuth.getInstance().currentUser?.displayName
                    intent.putExtra("name",username)
                    intent.putExtra("email",email)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignInActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

}