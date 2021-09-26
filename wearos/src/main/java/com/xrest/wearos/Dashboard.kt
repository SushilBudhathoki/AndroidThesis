package com.xrest.wearos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

currentFrag(CartFragment())


val bn:BottomNavigationView = findViewById(R.id.nav_view)
        bn.setOnNavigationItemSelectedListener(){

            when(it.itemId){
                R.id.navigation_home->{
                    currentFrag(FoodFragment())
                }
                R.id.navigation_cart->{
                    currentFrag(CartFragment())
                }

            }



             true
        }




    }

    fun currentFrag(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl,fragment)
            commit()
            addToBackStack(null)
        }
    }
}