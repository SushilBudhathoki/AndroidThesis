package com.xrest.finalassignment.UI

import com.xrest.finalassignment.Retrofit.UserRepo
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.xrest.finalassignment.MapsActivity
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class Login : AppCompatActivity(), View.OnClickListener , SensorEventListener {

    var permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private lateinit var btn: TextView
    private lateinit var etUser: EditText
    private lateinit var etPassword: EditText
    private lateinit var signup: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (!hasPermission()) {
            requestPermission()
        }
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }


        btn = findViewById(R.id.login)
        etUser = findViewById(R.id.name)
        etPassword = findViewById(R.id.password)
        signup = findViewById(R.id.signup)
        btn.setOnClickListener(this)
        signup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.login -> {
                login()

                var dialog = AlertDialog.Builder(this)
                dialog.setTitle("Save User")

                dialog.setMessage("Do you want to save the User?")
                dialog.setPositiveButton("Yes") {dialog,which->

                    val sharedPref = getSharedPreferences("Saved",MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("username",etUser.text.toString())
                    editor.putString("password",etPassword.text.toString())
                    editor.apply()
                    Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                    login()


                }
                dialog.setNegativeButton("No"){dialog, which ->
                    login()
                }
                val alert = dialog.create()

                alert.setCancelable(true)
                alert.show()

            }
            R.id.signup -> {


                startActivity(Intent(this@Login, MainActivity::class.java))

            }


        }
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this@Login,
                permissions, 1
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }

    fun login() {
        val us = UserRepo()
        CoroutineScope(Dispatchers.IO).launch {

            val response = us.Login(etUser.text.toString(), etPassword.text.toString())
            if (response.status == true) {
                val ans: TextView = findViewById(R.id.ans)
                RetrofitBuilder.token = "Bearer ${response.token!!}"
                RetrofitBuilder.user = response.data
                var intent = Intent(this@Login, Dashboard::class.java)
                startActivity(intent)
            } else {
                withContext(Main) {

                    Toast.makeText(this@Login, "Invalid Credentials", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null) {
            flag = false
        }
        return flag
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[1]
CoroutineScope(Dispatchers.IO).launch {
    if (values< -2)

    {

        startActivity(Intent(this@Login, MainActivity::class.java))
    }
    else if (values > 2)
    {
        startActivity(Intent(this@Login, MapsActivity::class.java))

    }

}


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


}