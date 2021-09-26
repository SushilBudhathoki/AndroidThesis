package com.xrest.finalassignment.UI

import com.xrest.finalassignment.Retrofit.UserRepo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Connection.CheckConnection
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class Splash : AppCompatActivity() {
    public var lst: ArrayList<Food> = ArrayList()
    private lateinit var lottie: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front)
        supportActionBar?.hide()
        lottie = findViewById(R.id.anim)

        val sharedPref = getSharedPreferences("Saved", MODE_PRIVATE)
        val username = sharedPref.getString("username", "").toString()
        val password = sharedPref.getString("password", "").toString()
        lottie.animate().translationY((1600).toFloat()).setDuration(5000).setStartDelay(1000)


        CheckConnection(this).checkRegisteredNetwork()
        CoroutineScope(Dispatchers.IO).launch {
try {

        val re = UserRepo().Login(username, password)

        if (re.status == true) {
            RetrofitBuilder.token = "Bearer ${re.token!!}"
            RetrofitBuilder.user = re.data
            delay(5000)
            startActivity(Intent(this@Splash, Dashboard::class.java))


        } else {
            startActivity(Intent(this@Splash, Login::class.java))

        }
        withContext(Main) {
            Toast.makeText(this@Splash, "", Toast.LENGTH_SHORT).show()
        }
        delay(3000)

}catch(ex:Exception){
    withContext(Main){
        Toast.makeText(this@Splash, "Server is not available", Toast.LENGTH_SHORT).show()

    }
   }
        }

    }
}











