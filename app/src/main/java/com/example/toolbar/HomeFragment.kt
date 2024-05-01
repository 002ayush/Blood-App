package com.example.toolbar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class HomeFragment() : Fragment() {






    private var navigationView : NavigationView? = null
    lateinit var myRecyclerView: RecyclerView
    lateinit var databaseReference: DatabaseReference
    companion object {

        private const val ARG_TEXT = "arg_text"
        private const val ARG_IMAGE_URL = "arg_image_url"

        fun getInstance(text: String, imageUrl: Int): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle().apply {
                putString(ARG_TEXT, text)
                putInt(ARG_IMAGE_URL, imageUrl)
            }
            fragment.arguments = args
            return fragment
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_home, container, false)
        myRecyclerView = view.findViewById(R.id.recyclerView)
        myRecyclerView.layoutManager = GridLayoutManager(context,2)
        val bloodgrps = arrayListOf<Blooddetails>()
        val bloodgroupsgiven = arrayOf("B+","A+","O+","O-","AB+","B-","A-","AB-")
        Log.d("Entering into the Ayush Mishra error","The adapter part")
        databaseReference = FirebaseDatabase.getInstance().getReference("bloodgrp")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bloodgrps.clear()
                for(bloods in bloodgroupsgiven) {
                    var bloodcnt = "0"
                    if (dataSnapshot.hasChild(bloods)){
                        bloodcnt = dataSnapshot.child(bloods).value.toString()
                    }
                    bloodgrps.add(Blooddetails(bloods,bloodcnt))
                }
                val myadapter = HomeAdapter(bloodgrps)
                myRecyclerView.adapter = myadapter

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle potential errors
                Log.e("FirebaseErrorwithAyushMishra", "Error fetching data", databaseError.toException())
            }/*
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onDataChange(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {}*/
        })

        return view




    }


}