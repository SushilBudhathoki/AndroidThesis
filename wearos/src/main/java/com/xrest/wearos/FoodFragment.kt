package com.xrest.wearos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_food, container, false)
        CoroutineScope(Dispatchers.IO).launch {
 val ur = FoodRepo()

            val response = ur.getFood()
            var list: MutableList<Food> = mutableListOf()
            list = response.data!!

            withContext(Dispatchers.Main) {
                setAdapter(list, view, container!!)
            }
        }
        return view
    }
    fun setAdapter(lst: MutableList<Food>, view: View, container: ViewGroup) {
        val rv: RecyclerView = view.findViewById(R.id.rv)
        val adapter = FoodAdapter(lst, container!!.context)
        rv.layoutManager = LinearLayoutManager(container!!.context)
        rv.adapter = adapter

    }

}