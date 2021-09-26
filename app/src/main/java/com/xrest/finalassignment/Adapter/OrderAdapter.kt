package com.xrest.finalassignment.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.xrest.finalassignment.Class.Order
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.UI.AboutLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class OrderAdapter(val lst:MutableList<Order>,val context: Context):RecyclerView.Adapter<OrderAdapter.OrderHolder>(){



    class OrderHolder(view: View):RecyclerView.ViewHolder(view){


                val total:TextView
                        val description:RecyclerView
                         val delete:ImageButton
                         val accept:Button
                         val deliver:Button
                         init {


                             total = view.findViewById(R.id.totalPrice)
                             description = view.findViewById(R.id.rv)
                             delete = view.findViewById(R.id.delete)
                             accept = view.findViewById(R.id.accepto)
                             deliver = view.findViewById(R.id.acceptd)
                         }





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order,parent,false)
        return OrderHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val order =lst[position]

        var desc =""
        holder.description.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
       holder.description.adapter= Food_Adpater(order.ProductId!!,context)
        holder.total.text = order.totalAmount

        holder.deliver.isVisible=false
        holder.accept.isVisible=false




        if(order.orderStatus==true){
            holder.delete.isVisible=false

        }
 if(RetrofitBuilder.user!!.UserType=="Admin"&&order.orderStatus==false){
    holder.accept.isVisible=true

}
else if(RetrofitBuilder.user!!.UserType=="Employee"&&order.orderStatus==true){
            holder.deliver.isVisible=true

        }
        if(order.orderStatus==true && order.DeliveredStatus==true){
    holder.delete.isVisible=false
    holder.deliver.isVisible=false
    holder.accept.isVisible=false

        }
        holder.delete.setOnClickListener() {

            CoroutineScope(Dispatchers.IO).launch {

                val repo = FoodRepo()
                val response = repo.cancelOrder(order._id.toString())
                if (response.success == true) {
                    withContext(Main) {
                        lst.removeAt(position)
                        notifyDataSetChanged()
                        Toast.makeText(
                            context,
                            "One Order Deleted From History",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }
            notifyDataSetChanged()
        }


            holder.accept.setOnClickListener(){
                CoroutineScope(Dispatchers.IO).launch {
try {
    val fr = FoodRepo()
    val repo = fr.approve(order._id.toString())
    if(repo.status==true){
        withContext(Main){
            Toast.makeText(context, " Order has been accepted waiting to deliver", Toast.LENGTH_SHORT).show()
        }
    }

}
catch (ex:Exception){

}
                    }




        }
        holder.deliver.setOnClickListener(){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val fr = FoodRepo()
                    val repo = fr.Deliver(order._id.toString())
                    if(repo.success==true){
                        RetrofitBuilder.latlng = LatLng( order.lat?.toDouble()!!,order.lng?.toDouble()!!)

                        withContext(Main){
                            context.startActivity(Intent(context,AboutLocation::class.java))
                            Toast.makeText(context, "Order is ready to deliver", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                catch (ex:Exception){

                }
            }




        }




    }
    override fun getItemCount(): Int {


       return lst.size
    }
}