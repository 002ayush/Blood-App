package com.example.toolbar


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class ProfileFragment : Fragment() {


    /*private lateinit var recyclerView: RecyclerView

    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth : FirebaseAuth*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_profile, container, false)
        /*recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val userDetails = ArrayList<UserDetails>()
        val myAdapter = ProfileAdapter(userDetails)
        mAuth = FirebaseAuth.getInstance()
        recyclerView.adapter = myAdapter
        databaseReference = FirebaseDatabase.getInstance().getReference()
        databaseReference.child("Users").addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                userDetails.clear()
                for(postSnapshot in snapshot.children){
                    val Useruid = postSnapshot.child("uid").value.toString()
                    val email = postSnapshot.child("email").value.toString()
                    val name = postSnapshot.child("name").value.toString()



                    if (mAuth.currentUser?.uid != Useruid){
                        val currentUser = UserDetails(name, email, Useruid)
                        userDetails.add(currentUser)


                }

                myAdapter.notifyDataSetChanged()
            }}

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError Message","This is the Error message changed in the database")
            }
        })




*/







        return  view
    }


}