package com.xrest.finalassignment.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.xrest.finalassignment.Class.Bookings
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.products
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleFood : AppCompatActivity(), View.OnClickListener {
    private lateinit var qty: TextView
    var food:Food?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_food)
        val foods = intent.getStringExtra("food")
        val name: TextView = findViewById(R.id.name)
        val img: ImageView = findViewById(R.id.productimage)
        val desc: TextView = findViewById(R.id.description)
        val price: TextView = findViewById(R.id.price)
        val rating: RatingBar = findViewById(R.id.ratingbar)
        val plus: ImageView = findViewById(R.id.plus)
        val minus: ImageView = findViewById(R.id.minus)
        val btn: TextView = findViewById(R.id.cart)
        qty = findViewById(R.id.quantity)



        CoroutineScope(Dispatchers.IO).launch {
            val fr = FoodRepo()
            val response = fr.getFoodId(foods.toString())
            if (response.status == true) {
                withContext(Main) {
                     food = response!!.data!!
                    name.text = food!!.Name
                    desc.text = food!!.Description
                    price.text = food!!.Price.toString()
                    rating.rating = food!!.Rating!!.toFloat()
                    Glide.with(this@SingleFood).load("${RetrofitBuilder.BASE_URL}images/${food!!.Image}").into(img)


                }
            }


        }


        btn.setOnClickListener(this)
        plus.setOnClickListener(this)
        minus.setOnClickListener(this)
    }

    var i = 0
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.plus -> {
                i = i + 1
                qty.text = i.toString()
            }
            R.id.minus -> {
                i = i - 1

                if (i <= 0) {
                    i = 0
                }
                qty.text = i.toString()
            }
            R.id.cart -> {

                CoroutineScope(Dispatchers.IO).launch {
                    val fr = FoodRepo()

                    val response = fr.book(food?._id!!.toString(),
                        Bookings(qty.text.toString().toInt())
                    )
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@SingleFood, "Food Booked", Toast.LENGTH_SHORT).show()
                        }
                    }

                }


            }
        }
    }
}