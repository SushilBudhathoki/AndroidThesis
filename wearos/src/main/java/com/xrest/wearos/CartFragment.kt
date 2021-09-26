package com.xrest.wearos

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment() {


    var lsts: ArrayList<products>? = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_cart, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv)

        CoroutineScope(Dispatchers.IO).launch {
            val fr = FoodRepo()
            val response = fr.getBooking()
            if (response.status == true && response.data!!.ProductId!!.size!=0 && response.data!!.ProductId !=null) {
                lsts = response.data!!.ProductId
                withContext(Dispatchers.Main) {


                    val adapter = BookAdapter(lsts!!, container!!.context)
                    recyclerView.layoutManager = LinearLayoutManager(container!!.context)
                    recyclerView.adapter = adapter
                }

            }
            else{

                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(), "Empty Cart", Toast.LENGTH_LONG).show()
               recyclerView.isVisible=false
                    recyclerView.isEnabled =false
                }

            }



        }
        return view
    }


}