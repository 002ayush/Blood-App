package com.example.toolbar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale


class People : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    private lateinit var myAdapter: ProfileAdapter
    private lateinit var userDetails: ArrayList<UserDetails>
    private var tempuserDetails: ArrayList<UserDetails> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userDetails = ArrayList()

        myAdapter = ProfileAdapter(userDetails,this)
        mAuth = FirebaseAuth.getInstance()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "People"
        recyclerView.adapter = myAdapter

        databaseReference = FirebaseDatabase.getInstance().getReference("donardetails")

        databaseReference.orderByChild("donarlasttimedonated").endBefore("2023-11-11").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                userDetails.clear()
                for(postSnapshot in snapshot.children) {
                    val Useruid = postSnapshot.child("uid").value.toString()
                    val email = postSnapshot.child("donaremailValue").value.toString()
                    val name = postSnapshot.child("donarname").value.toString()
                    val bloodgrp = postSnapshot.child("donarbloodgroupValue").value.toString()
                    if (mAuth.currentUser?.uid != Useruid) {
                        val currentUser = UserDetails(name, email, Useruid, bloodgrp)
                        userDetails.add(currentUser)
                    }
                    myAdapter.notifyDataSetChanged()
                }
                myAdapter.setOnitemClick(object : ProfileAdapter.ItemOnClickListener{
                        override fun onitemClicking(position: Int) {
                            //on clicking each item what action do I need to perform
                            val intent = Intent(this@People, ChatActivity::class.java)
                            intent.putExtra("name", userDetails[position].name)
                            intent.putExtra("uid", userDetails[position].uid)
                            startActivity(intent)
                        }



                })
                myAdapter.setOnitemClickCall(object : ProfileAdapter.ItemOnClickListener2{
                    override fun onitemClickingcall(position: Int) {
                        val teleno = userDetails[position].uid

                        databaseReference.child(teleno).addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val path = snapshot.child("donarphvalue").value.toString()
                                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$path")
                                }
                                if (dialIntent.resolveActivity(packageManager) != null) {
                                    startActivity(dialIntent)
                                } else {
                                    Toast.makeText(this@People, "Sorry Call is not available", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {}
                        })

                    }


                })
                }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError Message","This is the Error message changed in the database")
            }

        })

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
           }

            override fun onQueryTextChange(p0: String?): Boolean {

                Log.d("Debug mode on","Yaha message hai")
                val searchText = p0.orEmpty().toLowerCase(Locale.ROOT)
                tempuserDetails.clear()
                if (searchText.isNotEmpty()) {
                    userDetails.forEach { user ->
                            if (user.bloodgrp.toLowerCase(Locale.ROOT).contains(searchText)) {
                            tempuserDetails.add(user)
                        }
                    }
                    myAdapter.notifyDataSetChanged()
                }else{

                    tempuserDetails.addAll(userDetails)
                    myAdapter.notifyDataSetChanged()
                }

                myAdapter.updateList(tempuserDetails)
                return true
            }



        })

    }



}