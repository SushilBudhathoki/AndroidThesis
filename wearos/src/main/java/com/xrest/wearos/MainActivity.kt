package com.xrest.wearos

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : WearableActivity() {
    private lateinit var btn: TextView
    private lateinit var etUser: EditText
    private lateinit var etPassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.login)
        etUser = findViewById(R.id.name)
        etPassword = findViewById(R.id.password)


        btn.setOnClickListener(){

            CoroutineScope(Dispatchers.IO).launch {

                val repo = UserRepo()
                val response = repo.Login(etUser.text.toString(),etPassword.text.toString())
                if(response.status==true){
                    RetrofitBuilder.token="Bearer "+response.token!!
                    val intent = Intent(this@MainActivity,Dashboard::class.java)
                    startActivity(intent)


                }


            }

        }


        // Enables Always-on
        setAmbientEnabled()
    }
}