package com.xrest.wearos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class  BookAdapter(val lst: ArrayList<products>, val context: Context) : RecyclerView.Adapter<BookAdapter.BookingViewHolder>() {


    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val img: ImageView
        val name: TextView
        val qty: TextView
        val price: TextView
        val delete: ImageButton
        val plus :TextView
        val minus :TextView

        init {
            img = view.findViewById(R.id.foodImage)
            name = view.findViewById(R.id.foodName)
            qty = view.findViewById(R.id.foodQty)
            delete = view.findViewById(R.id.delete)
            price = view.findViewById(R.id.foodPrice)
plus = view.findViewById(R.id.plus)
            minus = view.findViewById(R.id.minus)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {


        val view = LayoutInflater.from(context).inflate(R.layout.booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booked = lst?.get(position)
        var food: Food? = booked!!.food
        holder.name.text = food!!.Name
        holder.qty.text = lst!!.get(position).qty.toString()
        holder.price.text = (food!!.Price!!.toInt() * booked.qty!!.toInt()).toString()
        Glide.with(context).load("${RetrofitBuilder.BASE_URL}images/${food!!.Image}").into(holder.img)

        holder.delete.setOnClickListener() {
            delete( position)
            notifyDataSetChanged()
        }
        holder.plus.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
               val repo = FoodRepo()
                val nqty = holder.qty.text.toString().toInt()+1
                val response = repo.updateBooking(lst[position].food!!._id!!, Bookings(nqty))

                    withContext(Main){
                        holder.price.text = (food!!.Price!!.toInt() * nqty).toString()
                        holder.qty.text = nqty.toString()
                        Toast.makeText(context, "Quantity Increased", Toast.LENGTH_SHORT).show()
                    }

            } }

        holder.minus.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                val repo = FoodRepo()
                val nqty = holder.qty.text.toString().toInt()-1
                val response = repo.updateBooking(lst[position].food!!._id!!, Bookings(nqty))


                    withContext(Main){
                        holder.price.text = (food!!.Price!!.toInt() * nqty).toString()

                        holder.qty.text = nqty.toString()
                        Toast.makeText(context, "${response.success}", Toast.LENGTH_SHORT).show()
                    }


            } }

        Toast.makeText(context, "${lst.size}", Toast.LENGTH_SHORT).show()
    }


    override fun getItemCount(): Int {
        return lst?.size!!
    }

    fun delete( position: Int) {
        val fr = FoodRepo()
        CoroutineScope(Dispatchers.IO).launch {
            val response = fr.delete(lst?.get(position)!!._id.toString())
            if (response.success == true) {

                withContext(Main) {
                    lst.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "one Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }




}