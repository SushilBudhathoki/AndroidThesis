package com.xrest.finalassignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import com.xrest.finalassignment.Adapter.BookAdapter
import com.xrest.finalassignment.Class.UserLat
import com.xrest.finalassignment.Fragmnet.ShowOrderFragment
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.UI.Dashboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap
    var cm:Marker?=null
    var fusedLocationProviderClient: FusedLocationProviderClient?=null
    var currentLocation:android.location.Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val next: Button = findViewById(R.id.next)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocatino()


        next.setOnClickListener(){

            val fr = FoodRepo()
            CoroutineScope(Dispatchers.IO).launch {
                val r = fr.UserOrder(UserLat(RetrofitBuilder.latlng.latitude!!.toString(),RetrofitBuilder.latlng.longitude.toString()))
                if(r.success==true){
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@MapsActivity,Dashboard::class.java))
                        Toast.makeText(this@MapsActivity, "Your Order Have Been Saved", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
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


    }



}


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {


        when(requestCode){

            100->{

                if(grantResults.size>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLocatino()
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
       googleMap=p0!!
        var latLng =LatLng(currentLocation?.latitude!!,currentLocation?.longitude!!)
        RetrofitBuilder.latlng = latLng
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f),4000,null)
        googleMap.uiSettings.isZoomControlsEnabled = true

        cm = googleMap.addMarker( MarkerOptions().position(latLng).title("Your Location ${latLng.latitude}, ${latLng.longitude}").
        snippet(getAddress(latLng.latitude,latLng.longitude)).draggable(true))
        cm?.showInfoWindow()

       googleMap.setOnMarkerDragListener(object:OnMarkerDragListener{
           override fun onMarkerDragStart(p0: Marker?) {

           }

           override fun onMarkerDrag(p0: Marker?) {

           }

           override fun onMarkerDragEnd(p0: Marker?) {
               if(cm!=null){
                   cm?.remove()
                   val newLat = LatLng(p0?.position?.latitude!!,p0?.position?.longitude!!)
                   RetrofitBuilder.latlng = newLat
                   drawMarker(newLat)
               }
           }
       })



    }

  private  fun drawMarker(latLng:LatLng){



        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f),4000,null)
        googleMap.uiSettings.isZoomControlsEnabled = true

        cm = googleMap.addMarker( MarkerOptions().position(latLng).title("Your Location ${latLng.latitude}, ${latLng.longitude}").
        snippet(getAddress(latLng.latitude,latLng.longitude)).draggable(true))
        cm?.showInfoWindow()
    }

    private fun getAddress(latitude: Double, longitude: Double):String {
val gc= Geocoder(this, Locale.getDefault())
       val arrayAddress= gc.getFromLocation(latitude,longitude,1)
        return arrayAddress[0].getAddressLine(0).toString()
    }
}




































//        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(OnMapReadyCallback {
//            googleMap = it
//            Log.d("GoogleMap", "before isMyLocationEnabled")
////            googleMap.isMyLocationEnabled = true
//            val location1 = LatLng(13.0356745, 77.5881522)
//            googleMap.addMarker(MarkerOptions().position(location1).title("My Location"))
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 5f))
//
//            Log.d("GoogleMap", "before location2")
//            val location2 = LatLng(9.89, 78.11)
//            googleMap.addMarker(MarkerOptions().position(location2).title("Madurai"))
//
//            Log.d("GoogleMap", "before location3")
//
//            val location3 = LatLng(13.029727, 77.5933021)
//            googleMap.addMarker(MarkerOptions().position(location3).title("Bangalore"))

//            Log.d("GoogleMap", "before URL")
//            val URL = getDirectionURL(location1, location3)
//            Log.d("GoogleMap", "URL : $URL")
//            GetDirection(URL).execute()

//        })




//    fun getDirectionURL(origin: LatLng, dest: LatLng): String {
//        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving"
//    }
//
//    private inner class GetDirection(val url: String) : AsyncTask<Void, Void, List<List<LatLng>>>() {
//        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
//            val response = client.newCall(request).execute()
//            val data = response.body()!!.string()
//            Log.d("GoogleMap", " data : $data")
//            val result = ArrayList<List<LatLng>>()
//            try {
//                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)
//
//                val path = ArrayList<LatLng>()
//
//                for (i in 0..(respObj.routes[0].legs[0].steps.size - 1)) {
////                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
////                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
////                    path.add(startLatLng)
////                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
////                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
//                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
//                }
//                result.add(path)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return result
//        }


//
//    public fun decodePolyline(encoded: String): List<LatLng> {
//
//        val poly = ArrayList<LatLng>()
//        var index = 0
//        val len = encoded.length
//        var lat = 0
//        var lng = 0
//
//        while (index < len) {
//            var b: Int
//            var shift = 0
//            var result = 0
//            do {
//                b = encoded[index++].toInt() - 63
//                result = result or (b and 0x1f shl shift)
//                shift += 5
//            } while (b >= 0x20)
//            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lat += dlat
//
//            shift = 0
//            result = 0
//            do {
//                b = encoded[index++].toInt() - 63
//                result = result or (b and 0x1f shl shift)
//                shift += 5
//            } while (b >= 0x20)
//            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
//            lng += dlng
//
//            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
//            poly.add(latLng)
//        }
//
//        return poly
//    }


