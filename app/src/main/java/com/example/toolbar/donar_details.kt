package com.example.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class donar_details : Fragment() {

    lateinit var databaseReference: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_donar_details, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference("donardetails")
        val donarname = view.findViewById<TextView>(R.id.etdonarName)
        val donarid = FirebaseAuth.getInstance().currentUser?.uid
        val bloodgrp = view.findViewById<TextInputEditText>(R.id.donarbloodgrp)
        val gender = view.findViewById<TextInputEditText>(R.id.donargender)
        val phoneno = (view.findViewById<TextInputEditText>(R.id.donarphoneno))
        val emailId =( view.findViewById<TextView>(R.id.donaremailId))
        val occupation = (view.findViewById<TextInputEditText>(R.id.donaroccupation))
        val city = (view.findViewById<TextInputEditText>(R.id.donarcity))
        val temperature = (view.findViewById<TextInputEditText>(R.id.donartemperature))
        val BP =  (view.findViewById<TextInputEditText>(R.id.donarBP))
        val pulseRate =( view.findViewById<TextInputEditText>(R.id.donarpulse))
        val weight = (view.findViewById<TextInputEditText>(R.id.donarweight))
        val donatedpreviously = view.findViewById<TextInputEditText>(R.id.donarpreviouslydonated)
        /*val donatedpreviously =( view.findViewById<RadioGroup>(R.id.donardonatedpreviously)).checkedRadioButtonId
        val donatedpreviouslyvalue = (view.findViewById<RadioButton>(donatedpreviously)).text.toString()*/
        val lasttimedonated = (view.findViewById<TextInputEditText>(R.id.etbloodlastdonated))
        donarname.text = FirebaseAuth.getInstance().currentUser?.displayName.toString()
        emailId.text = FirebaseAuth.getInstance().currentUser?.email.toString()
        val btnSubmit = view.findViewById<Button>(R.id.btndonardetails)
        btnSubmit.setOnClickListener {


            val donarnameValue = FirebaseAuth.getInstance().currentUser?.displayName.toString()

            val uid = donarid.toString()
            val donarbloodgroupValue = bloodgrp.text.toString()
            val donargenderValue = gender.text.toString()
            val donarphvalue = phoneno.text.toString()
            val donaremailValue = FirebaseAuth.getInstance().currentUser?.email.toString()
            val donaroccupation = occupation.text.toString()
            val donarCity = city.text.toString()
            val donartemp = temperature.text.toString()
            val donarBP = BP.text.toString()
            val donarpulseRate = pulseRate.text.toString()
            val donarweight = weight.text.toString()
            val donarpreviouslydonatedValue = donatedpreviously.text.toString()
            val donarlasttimedonated = lasttimedonated.text.toString()
            databaseReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {
                            val databaseChildReference =
                                databaseReference.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                            if (donarnameValue.isNotEmpty() && donarbloodgroupValue.isNotEmpty() && donargenderValue.isNotEmpty() && donarpreviouslydonatedValue.isNotEmpty() && donarphvalue.isNotEmpty() && donaremailValue.isNotEmpty() && donaroccupation.isNotEmpty() && donarCity.isNotEmpty() && donartemp.isNotEmpty() && donarBP.isNotEmpty() && donarpulseRate.isNotEmpty() && donarweight.isNotEmpty()) {


                                val donardetails = DonarDetails(
                                    donarnameValue,
                                    uid,
                                    donarbloodgroupValue,
                                    donargenderValue,
                                    donarphvalue,
                                    donaremailValue,
                                    donaroccupation,
                                    donarCity,
                                    donartemp,
                                    donarBP,
                                    donarpulseRate,
                                    donarweight,
                                    donarpreviouslydonatedValue,
                                    donarlasttimedonated

                                )

                                databaseChildReference.setValue(donardetails).addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Your Details Added Successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    val blooddatabaseReference =
                                        FirebaseDatabase.getInstance().getReference("bloodgrp")
                                    blooddatabaseReference.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {


                                            val previousValue =
                                                dataSnapshot.child(donarbloodgroupValue).value.toString()
                                                    .toInt()
                                            val newValue = previousValue + 1

                                            // Update the value of key2
                                            blooddatabaseReference.child(donarbloodgroupValue)
                                                .setValue(newValue)
                                                .addOnSuccessListener {
                                                    // Update successful
                                                }
                                                .addOnFailureListener { exception ->
                                                    // Handle errors
                                                }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                            // Handle errors
                                        }
                                    })
                                    (activity as MainActivity).loadFragment(HomeFragment())

                                }.addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Server Problem Issue",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Fields should not be empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }
        return view
    }

}