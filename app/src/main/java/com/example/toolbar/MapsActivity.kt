package com.example.toolbar

import android.content.pm.PackageManager
import  android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.maps.GeoApiContext
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.toolbar.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode.Color
import com.google.maps.DirectionsApi
import com.google.maps.android.PolyUtil
import com.google.maps.model.DirectionsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var databaseReference: DatabaseReference
    private var ApiKey : String? = null
    companion object {
        private const val  REQUEST_LOCATION_PERMISSION = 1000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val packageManager = this.packageManager
        val applicationInfo = packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
        val metaData = applicationInfo.metaData
      
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
<<<<<<< HEAD
=======

       


>>>>>>> 961b66c71a28d41e6df9935eda68172ca6b44434
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }else{
            getLastLocation()


        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, get the location
                    getLastLocation()


                } else {
                    // Permission denied, handle accordingly
                    Log.e("Denied Permission", "Location permission denied")
                }
            }
        }
    }
    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        // Handle the location here
                        val latitude = it.latitude
                        val longitude = it.longitude
                        Log.d("Value of Latitude and Longitude", "Latitude: $latitude, Longitude: $longitude")
                        val latlng = LatLng(latitude, longitude)
                        mMap.addMarker(MarkerOptions().position(latlng).title("B+"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,19f))
                        val geocoder = Geocoder(this, Locale.ROOT)
                        geocoder.getFromLocation(latitude,longitude,1
                        ) { loc ->
                            val addresses = loc[0].getAddressLine(0)
                            FirebaseDatabase.getInstance().getReference("donardetails").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("donarCity").setValue(addresses).addOnSuccessListener { Log.d("Msg for Address Updation","Address Added") }


                        }


                    }
                }
            fetchuserblooddetails()
        } else {

            Log.e("Location Permission denied", "Location permission not granted")
        }
    }

    private fun fetchuserblooddetails() {
        databaseReference = FirebaseDatabase.getInstance().getReference("donardetails")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children) {
                    if (snap.child("uid").value.toString() != FirebaseAuth.getInstance().currentUser?.uid.toString()){
                    val donarloc = snap.child("donarCity").value.toString()
                        val bloodgrp = snap.child("donarbloodgroupValue").value.toString()
                    geocodeAddressAndMarker(donarloc,bloodgrp)}
                }
            }



            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun geocodeAddressAndMarker(address: String,title:String) {
        val geocoder = Geocoder(this)
        try {
            val locationList = geocoder.getFromLocationName(address, 1)!!
            if (locationList.isNotEmpty()) {
                val latLng = LatLng(locationList[0].latitude, locationList[0].longitude)
                val marker = mMap.addMarker(MarkerOptions().position(latLng))
                marker?.tag = title

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

<<<<<<< HEAD


}
=======
 

}
>>>>>>> 961b66c71a28d41e6df9935eda68172ca6b44434
