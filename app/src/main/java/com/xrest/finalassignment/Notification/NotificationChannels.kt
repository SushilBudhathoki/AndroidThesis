package com.xrest.finalassignment.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationChannels(val context: Context) {


    val c1:String="Channel1"
    val c2:String="Channel2"
    fun createNotification(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {

            val channel1 = NotificationChannel(
                c1,"Channel1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description="This is 1"
            val channel2 = NotificationChannel(
                c2,"Channel2",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel2.description="This is 2`"
val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)

        }


    }







}