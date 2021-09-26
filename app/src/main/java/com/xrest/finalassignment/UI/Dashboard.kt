package com.xrest.finalassignment.UI

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xrest.finalassignment.Fragmnet.*
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder

private lateinit var fl: FrameLayout
private lateinit var bn: BottomNavigationView

class Dashboard : AppCompatActivity() , SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
        fl = findViewById(R.id.fl)
        bn = findViewById(R.id.nav_view)


//        var type = RetrofitBuilder.user!!.UserType
//
//        when(type){
//
//            "Admin"->{
//                currentFragmnet(AdminFragment())
//                bn.isVisible=false
//            }
//            "Customer"->{
//                currentFragmnet(FoodShowFragment())
//            }
//            "Employee"->{
//                currentFragmnet(AdminFragment())
//                bn.isVisible=false
//            }
//        }

       currentFragmnet(FoodShowFragment())



            bn.setOnNavigationItemSelectedListener() {
                when (it.itemId) {
                    R.id.navigation_home -> {

                        currentFragmnet(FoodShowFragment())
                    }
                    R.id.navigation_dashboard -> {
                        currentFragmnet(ShowOrderFragment())
                    }
                    R.id.navigation_notifications -> currentFragmnet(UserFragment())
                    R.id.navigation_cart -> currentFragmnet(ShowBooking())
                    R.id.rest -> currentFragmnet(RestFragment())




                }


                true
            }




                true
            }








    fun currentFragmnet(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fl, fragment)
            addToBackStack(null)
            commit()


        }


    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values = event!!.values[0]

        if(values<=4) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            RetrofitBuilder.token = null
            RetrofitBuilder.user = null
            val prefs = getSharedPreferences("Saved", MODE_PRIVATE);
            val editor = prefs?.edit()
            if (editor != null) {
                editor.clear()
                editor.apply()
            }
            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            startActivity(intent);
            finish();
        }
        else {


        }


        }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            flag = false
        }
        return flag
    }

}




