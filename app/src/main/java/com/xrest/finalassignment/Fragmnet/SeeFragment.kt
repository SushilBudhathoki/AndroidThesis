package com.xrest.finalassignment.Fragmnet

import com.xrest.finalassignment.Retrofit.UserRepo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.xrest.finalassignment.Adapter.UserAdapter
import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.R
import com.xrest.finalassignment.RoomDatabase.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var lst: MutableList<Person> = mutableListOf()
        val view = inflater.inflate(R.layout.fragment_see, container, false)
        CoroutineScope(Dispatchers.IO).launch {


            val ur = UserRepo()

            val response = ur.getData()
            var list: MutableList<Person> = mutableListOf()
            list = response.data!!
            lst = UserDB.getInstance(container!!.context).getDAO().loadAllUsers()


            if (list.size > lst.size) {
                lst.clear()
                lst = list

            }



            withContext(Main) {
                Toast.makeText(container!!.context, " == ${lst.size}==${list.size}  ", Toast.LENGTH_SHORT).show()
            }



            withContext(Dispatchers.Main) {
                val rv: RecyclerView = view.findViewById(R.id.rvx)
//                val adapter = UserAdapter(lst, container!!.context)

                rv.layoutManager = LinearLayoutManager(container!!.context)
//                rv.adapter = adapter


            }

        }

        return view
    }


}