package com.xrest.finalassignment.Fragmnet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xrest.finalassignment.Adapter.FoodAdapter
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.RoomDatabase.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodShowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var lst: MutableList<Food> = mutableListOf()
        val view = inflater.inflate(R.layout.fragment_food_show, container, false)
        CoroutineScope(Dispatchers.IO).launch {


            val ur = FoodRepo()

            val response = ur.getFood()
            var list: MutableList<Food> = mutableListOf()
            list = response.data!!
            lst = UserDB.getInstance(container!!.context).getFoodDAO().loadAllFood()


            if (list.size > lst.size) {
                CoroutineScope(Dispatchers.IO).launch {
                    UserDB.getInstance(container!!.context).getFoodDAO().deleteAll()

                    for (data in list) {
                        UserDB.getInstance(container.context).getFoodDAO().InsertFood(data)
                    }
                    lst = UserDB.getInstance(container.context).getFoodDAO().loadAllFood()


                }

            }
            withContext(Dispatchers.Main) {
                setAdapter(lst, view, container)
            }
        }


        val logo: ImageView = view.findViewById(R.id.logo)
        logo.setOnClickListener() {

            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fl, FoodShowFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()

        }
        val search:EditText = view.findViewById(R.id.editSearch)
        val btn:ImageButton =view.findViewById(R.id.search)
        btn.setOnClickListener(){

            CoroutineScope(Dispatchers.IO).launch{
val repo = FoodRepo()
                val response = repo.search(search.text.toString())
                withContext(Dispatchers.Main) {
                    setAdapter(response.data!!, view, container!!)
                }



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






