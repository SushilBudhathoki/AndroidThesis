package com.xrest.finalassignment.Connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager

class CheckConnection(val context: Context) {
    var broadcastReceiver: BroadcastReceiver = ConnReceiver()

    fun checkRegisteredNetwork()
    {
        context.registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }


    fun unregisteredNetwork()
    {
        try {
            context.unregisterReceiver(broadcastReceiver)
        }
        catch (ex: Exception)
        {
            ex.printStackTrace()
        }
    }
}
