package com.xrest.finalassignment.Fragmnet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.BookAdapter
import com.xrest.finalassignment.Class.UserLat
import com.xrest.finalassignment.Class.products
import com.xrest.finalassignment.MapsActivity
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShowBooking : Fragment() {

    var lsts: ArrayList<products>? = ArrayList()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_show_booking, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv)


        val btn: Button = view.findViewById(R.id.order)
        CoroutineScope(Dispatchers.IO).launch {
            val fr = FoodRepo()
            val response = fr.getBooking()
            if (response.status == true && response.data!!.ProductId!!.size!=0 && response.data!!.ProductId !=null) {
                lsts = response.data!!.ProductId
                withContext(Main) {
                    lsts!!.reverse()
                    val adapter = BookAdapter(lsts!!, container!!.context)
                    recyclerView.layoutManager = LinearLayoutManager(container!!.context)
                    recyclerView.adapter = adapter
                }

            }
            else{

                withContext(Main){
                    btn.isVisible=false
                    val cart:TextView = view.findViewById(R.id.cart)
                    cart.text = "Your Cart is Empty"

                    recyclerView.isVisible=false
                    recyclerView.isEnabled =false
                }

            }



        }







        btn.setOnClickListener() {
            var intent = Intent(requireContext(), MapsActivity::class.java);
            startActivity(intent)


        }





        return view
    }
}