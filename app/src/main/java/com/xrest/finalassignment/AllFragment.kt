package com.xrest.finalassignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.OrderAdapter
import com.xrest.finalassignment.Class.Order
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AllFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        val rv: RecyclerView = view.findViewById(R.id.rv)
        val total:TextView = view.findViewById(R.id.total)
        var value =0
        var lst: MutableList<Order> = mutableListOf()





                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val foodRepo = FoodRepo()

                        if (RetrofitBuilder.user!!.UserType == "Employee") {
                            val response = foodRepo.emp()
                            lst = response.data!!
                            value = response.total!!.toInt()!!
                        } else {
                            val response = foodRepo.getAllOrders()
                            lst = response.data!!
                            value = response.total!!.toInt()!!

                        }


                        withContext(Dispatchers.Main) {
                            total.text = "Total Earnings: "+value.toString()!!
                            rv.layoutManager = LinearLayoutManager(
                                container!!.context,
                                LinearLayoutManager.VERTICAL, false
                            )
                            rv.adapter = OrderAdapter(lst, container!!.context)
                        }

                    }
                    catch (ex:Exception){

                    }
                }



        return view
    }


}