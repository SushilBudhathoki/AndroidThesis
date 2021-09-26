package com.xrest.finalassignment.Connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.xrest.finalassignment.Retrofit.RetrofitBuilder

class ConnReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val con = Connection(context)
        if(con.getConnection())
        {
//            RetrofitBuilder.online = true
            Toast.makeText(context, "You are online", Toast.LENGTH_SHORT).show()
        }
        else{

            RetrofitBuilder.online = false
            Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
        }

      }
}