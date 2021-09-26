package com.xrest.finalassignment.Fragmnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.OrderAdapter
import com.xrest.finalassignment.Class.Order
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ShowOrderFragment : Fragment() {
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_show_order, container, false)


    val rv:RecyclerView = view.findViewById(R.id.rvx)
        var lst: MutableList<Order> = mutableListOf()
val type= RetrofitBuilder.user!!.UserType

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val foodRepo = FoodRepo()

       withContext(Dispatchers.Main){
           when(type){
               "Admin" -> {

                   val response = foodRepo.unApproved()
                   lst = response.data!!


               }
               "Customer"->{
                   val response = foodRepo.getAllUserOrder()
                   lst = response.data!!
               }
               "Employee"->{
                   val response = foodRepo.unDelivered()
                   lst = response.data!!

               }

           }
           lst.reverse()
           rv.layoutManager = LinearLayoutManager(container!!.context,LinearLayoutManager.VERTICAL,false)
           val adapter = OrderAdapter(lst,requireContext())
           rv.adapter = adapter



                }

            }
            catch(ex:Exception)
            {
                print(ex.toString())
            }



        }

        return view
    }

}