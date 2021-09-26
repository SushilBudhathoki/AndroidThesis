package com.xrest.finalassignment.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.products
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder

class Food_Adpater(val lst: MutableList<products>, val context: Context) : RecyclerView.Adapter<Food_Adpater.FoodHolder>() {

    class FoodHolder(view: View) : RecyclerView.ViewHolder(view) {


        val img: ImageView
val name:TextView
        val price:TextView
        val price2:TextView

        init {

            img = view.findViewById(R.id.image)
            name = view.findViewById(R.id.name)
            price = view.findViewById(R.id.price)
            price2 = view.findViewById(R.id.price2)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_show, parent, false)
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val food = lst[position].food

        Glide.with(context).load("${RetrofitBuilder.BASE_URL}images/${food!!.Image}").into(holder.img)

        holder.name.text = food!!.Name
        holder.price.text = "$ ${food.Price}"

        holder.price2.text = "X ${lst[position].qty}"
    }

}