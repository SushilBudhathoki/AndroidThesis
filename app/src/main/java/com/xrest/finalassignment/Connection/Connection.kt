package com.xrest.finalassignment.Connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import java.lang.Exception


@Suppress("Deprecation")
class Connection(val context: Context) {


    fun getConnection():Boolean{
     val conn = true
        val connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) as NetworkInfo
        val data = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) as NetworkInfo
        if(wifi.isConnected || data.isAvailable)
        {

            return conn
        }


        return false
    }



}


