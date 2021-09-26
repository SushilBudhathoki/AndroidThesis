package com.xrest.finalassignment.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.Fragmnet.FoodAddFragment
import com.xrest.finalassignment.Fragmnet.RestFragment
import com.xrest.finalassignment.R
import com.xrest.finalassignment.UI.RestFoodActivity
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import java.util.ArrayList

class RestaurantAdapter(val lst: MutableList<Restuarant>, val fm: FragmentManager, val context: Context) : RecyclerView.Adapter<RestaurantAdapter.ResturantHolder>() {

    class ResturantHolder(view: View) : RecyclerView.ViewHolder(view) {


        var img: ImageView
        var name: TextView
        var desc: TextView
        var price: TextView
        var btn: TextView
        var rating: RatingBar
        var btn2: TextView


        init {

            img = view.findViewById(R.id.img)
            name = view.findViewById(R.id.name)
            desc = view.findViewById(R.id.description)
            price = view.findViewById(R.id.price)
            btn = view.findViewById(R.id.book)
            btn2 = view.findViewById(R.id.book2)
            rating = view.findViewById(R.id.ratingbar)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResturantHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mero_restro, parent, false)
        return ResturantHolder(view)
    }

    override fun onBindViewHolder(holder: ResturantHolder, position: Int) {
        val current = lst[position]
if(RetrofitBuilder.user?.UserType =="Admin"){
    holder.btn2.isVisible=true
}
        else{
    holder.btn2.isVisible=false
        }
        holder.name.text = current.name
        holder.desc.text="You can visit us on ${current.address}"
        holder.price.text = current.phone
        holder.rating.rating = current.rating!!.toFloat()

        Glide.with(context).load("${RetrofitBuilder.BASE_URL}images/${current.images}").into(holder.img)
        holder.btn.setOnClickListener() {
            val intent = Intent(context, RestFoodActivity::class.java)
            intent.putExtra("lst", ArrayList(current.foods))
            context.startActivity(intent)

        }
        holder.btn2.setOnClickListener() {

            RetrofitBuilder.rid = current._id
            val fragment = RestFragment()

            fm.beginTransaction()
                    .replace(R.id.fl, FoodAddFragment())
                    .disallowAddToBackStack()
                    .commit()
            Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {

        return lst.size

    }


}