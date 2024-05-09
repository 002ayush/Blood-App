package com.example.toolbar

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MeraProfile : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storage : FirebaseStorage
    private lateinit var selectedImage : Uri
    private lateinit var pfimage : CircleImageView
    private lateinit var databaseProfileReference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_mera_profile, container, false)
        val pfname = view.findViewById<TextView>(R.id.pfname)
        val pfbloodgrp = view.findViewById<TextView>(R.id.pfbloodgrp)
        val pfoccupation = view.findViewById<EditText>(R.id.pfoccupation)
        val lastdonated = view.findViewById<EditText>(R.id.pflastdonated)
        pfimage = view.findViewById(R.id.mera_profile_image)
        val pfcity = view.findViewById<EditText>(R.id.pflocation)
        val updatebtn  = view.findViewById<Button>(R.id.updatebtn)
        val userid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.d("user Id",userid)
        storage = FirebaseStorage.getInstance()
        pfimage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,1)
        }
        databaseProfileReference = FirebaseDatabase.getInstance().getReference("ProfileImage")
        databaseProfileReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profilephoto = snapshot.child("userProfileImage").value.toString()
                Picasso.get().load(profilephoto).into(pfimage)
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        databaseReference =
            FirebaseDatabase.getInstance().getReference("donardetails")
        databaseReference.child(userid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val username = FirebaseAuth.getInstance().currentUser?.displayName.toString()
                val userbloodgrp = snapshot.child("donarbloodgroupValue").value.toString()
                val useroccupation = snapshot.child("donaroccupation").value.toString()
                val userlastdonated = snapshot.child("donarlasttimedonated").value.toString()
                val usercity = snapshot.child("donarCity").value.toString()

                pfname.text = username
                pfbloodgrp.text = userbloodgrp

                pfoccupation.setText(useroccupation)
                lastdonated.setText(userlastdonated)
                pfcity.setText(usercity)

            }


            override fun onCancelled(error: DatabaseError) { }
        })
        updatebtn.setOnClickListener {

            databaseReference.child(userid).child("donaroccupation").setValue(pfoccupation.text.toString()).addOnSuccessListener { Log.d("user message","Added successfully") }.addOnFailureListener {
                Log.d("User message","Not added successfully")
            }
            databaseReference.child(userid).child("donarlasttimedonated").setValue(lastdonated.text.toString()).addOnSuccessListener { Log.d("user message","Added successfully") }.addOnFailureListener {
                Log.d("User message","Not added successfully")
            }
            databaseReference.child(userid).child("donarCity").setValue(pfcity.text.toString()).addOnSuccessListener { Log.d("user message","Added successfully") }.addOnFailureListener {
                Log.d("User message","Not added successfully")
            }
            val reference = storage.reference.child("ProfileImage").child(FirebaseAuth.getInstance().uid.toString())
            reference.putFile(selectedImage).addOnCompleteListener{
                if (it.isSuccessful){
                    reference.downloadUrl.addOnSuccessListener { image->
                        updateInfo(image.toString())
                    }
                }
            }
            val intent = Intent(context,MainActivity::class.java)


            startActivity(intent)

        }

        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (data.data != null){
                selectedImage = data.data!!
                pfimage.setImageURI(selectedImage)

            }
        }
    }

    private fun updateInfo(image : String) {
        if (image.isNotEmpty()){
        val userprofileimage = UserProfileImage(image,FirebaseAuth.getInstance().uid.toString())
        databaseReference = FirebaseDatabase.getInstance().getReference("ProfileImage")
        databaseReference.child(FirebaseAuth.getInstance().uid.toString()).setValue(userprofileimage).addOnSuccessListener { Log.d("ImageUploaded","Yes the image has been uploaded in storage") }
    }}


}