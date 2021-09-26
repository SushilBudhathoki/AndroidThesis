package com.xrest.finalassignment.UI

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.xrest.finalassignment.R

private lateinit var fusedLocationClient: FusedLocationProviderClient

class MapsFragment : Fragment() {

    var map: GoogleMap? = null
    var l: LatLng = LatLng(0.0, 0.0)


    private val callback = OnMapReadyCallback { googleMap ->
        val map = googleMap


        val sydney = LatLng(27.6643795, 85.3224007)

        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        map.addMarker(MarkerOptions().position(sydney).title("Y&B"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val sydney2 = LatLng(27.6671736, 85.3320173)
        map.addMarker(MarkerOptions().position(sydney2).title("Y&B"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney2, 16F), 4000, null)
        map.uiSettings.isZoomControlsEnabled = true


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_maps, container, false)




        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }





}