package com.xrest.finalassignment.UI

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import java.util.*

class AboutLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    var cm: Marker?=null
    var fusedLocationProviderClient: FusedLocationProviderClient?=null
    var currentLocation:android.location.Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_location)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    fun fetchLocatino(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }
        var task = fusedLocationProviderClient?.lastLocation


        task?.addOnSuccessListener { location->
            if(location!=null){
                this.currentLocation=location
                var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
                var latLng =RetrofitBuilder.latlng
                drawMarker(latLng)

            }



        }


    }
    fun drawMarker(latLng:LatLng){

  map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f),4000,null)
        map.uiSettings.isZoomControlsEnabled = true

        cm = map.addMarker( MarkerOptions().position(latLng).title("Your Location ${latLng.latitude}, ${latLng.longitude}").
        snippet(getAddress(latLng.latitude,latLng.longitude)).draggable(true))
        cm?.showInfoWindow()
    }

    private fun getAddress(latitude: Double, longitude: Double):String {
        val gc= Geocoder(this, Locale.getDefault())
        val arrayAddress= gc.getFromLocation(latitude,longitude,1)
        return arrayAddress[0].getAddressLine(0).toString()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map =googleMap
        val sydney2 = RetrofitBuilder.latlng
       drawMarker(sydney2)
    }
}