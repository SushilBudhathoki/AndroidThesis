package com.xrest.finalassignment.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.FoodAdapter
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.R

class RestFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_food)
        var lst = mutableListOf<Food>()
        lst = intent.getParcelableArrayListExtra<Food>("lst") as MutableList<Food>
        val rvv: RecyclerView = findViewById(R.id.rvv)
        rvv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvv.adapter = FoodAdapter(lst, this)


    }
}