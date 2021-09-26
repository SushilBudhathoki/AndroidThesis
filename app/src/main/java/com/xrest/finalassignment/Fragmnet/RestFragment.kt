package com.xrest.finalassignment.Fragmnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.RestaurantAdapter
import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rest, container, false)
        var lst: MutableList<Restuarant> = mutableListOf()

        CoroutineScope(Dispatchers.IO).launch {
            val foodRepo = FoodRepo()
            val response = foodRepo.getRest()
            if (response.success == true) {
                lst = response.data!!
                withContext(Dispatchers.Main) {
                    val rv: RecyclerView = view.findViewById(R.id.rv)
                    rv.layoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.VERTICAL, false)
                    rv.adapter = RestaurantAdapter(lst, requireFragmentManager(), container!!.context)
                }
            }


        }



        return view
    }

}