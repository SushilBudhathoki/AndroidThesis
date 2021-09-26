package com.xrest.finalassignment.Adapter

import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xrest.finalassignment.Class.Bookings
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.products
import com.xrest.finalassignment.Notification.NotificationChannels
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import com.xrest.finalassignment.UI.SingleFood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodAdapter(val lst: MutableList<Food>, val context: Context) : RecyclerView.Adapter<FoodAdapter.FoodHolder>() {


    class FoodHolder(view: View) : RecyclerView.ViewHolder(view) {


        var img: ImageView
        var name: TextView
        var desc: TextView
        var price: TextView
        var btn: TextView
        var rating: RatingBar



        init {

            img = view.findViewById(R.id.img)
            name = view.findViewById(R.id.name)
            desc = view.findViewById(R.id.description)
            price = view.findViewById(R.id.price)
            btn = view.findViewById(R.id.book)
            rating = view.findViewById(R.id.ratingbar)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mero_food, parent, false)
        return FoodHolder(view)
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        val food = lst[position]
        holder.name.text = food.Name
        holder.desc.text = food.Description
        holder.price.text = "Rs " + food.Price.toString()
        holder.rating.rating = food!!.Rating!!.toFloat()
        Glide.with(context).load("${RetrofitBuilder.BASE_URL}images/${food.Image}").into(holder.img)


        holder.img.setOnClickListener() {
            val intent = Intent(context, SingleFood::class.java)
            val food = Food(
                    _id = lst[position]._id,
                    Name = lst[position].Name,
                    Image = lst[position].Image,
                    Price = lst[position].Price,
                    Rating = lst[position].Rating,
                    Description = lst[position].Description
            )
            intent.putExtra("food", lst[position]._id)
            context.startActivity(intent)


        }


        holder.btn.setOnClickListener() {
//           dialog(lst[position])
            book(food)
        }


    }


    fun dialog(food: Food) {
        val d = AlertDialog.Builder(context)
        d.setTitle("Book Confirmation")

        d.setMessage("Are you sure you want to book this item?")
        d.setPositiveButton("Yes") { dialog, which ->

            book(food)
            HighNotification()
            Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
            dialog.cancel()

        }
        d.setNegativeButton("No") { dialog, which ->

        }
        val alert = d.create()

        alert.setCancelable(true)
        alert.show()


    }

    fun book(food: Food) {
        CoroutineScope(Dispatchers.IO).launch {
            val fr = FoodRepo()

            val response = fr.book(food._id!!.toString(),Bookings(1))
            if (response.success == true) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Food Booked", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    fun HighNotification(){

        val notificationManager= NotificationManagerCompat.from(context)
        val notificationChannels  = NotificationChannels(context)
        notificationChannels.createNotification()
        val notification = NotificationCompat.Builder(context,notificationChannels.c1)

            .setSmallIcon(R.drawable.ic_baseline_fastfood_24)
            .setContentTitle("Added To Cart")
            .setContentTitle("One Item Added To Cart").setColor(Color.BLUE).build()
        notificationManager.notify(1,notification)
    }


}