//package com.xrest.finalassignment.Adapter
//
//import com.xrest.finalassignment.Retrofit.UserRepo
//import android.app.Dialog
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.xrest.finalassignment.Class.Person
//import com.xrest.finalassignment.Models.User
//import com.xrest.finalassignment.R
//import com.xrest.finalassignment.Retrofit.RetrofitBuilder
//import com.xrest.finalassignment.RoomDatabase.UserDB
//import com.xrest.finalassignment.UI.MainActivity
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.lang.Exception
//
//class UserAdapter(var lst: MutableList<Person>, var context: Context) : RecyclerView.Adapter<UserAdapter.UserHolder>() {
//
//    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
//
//        var fullname: TextView
//        var username: TextView
//        var gender: TextView
//        var img: ImageView
//        var delete: ImageButton
//        var update: ImageButton
//
//        init {
//            fullname = view.findViewById(R.id.fullname)
//            username = view.findViewById(R.id.name)
//            gender = view.findViewById(R.id.gender)
//            img = view.findViewById(R.id.prof)
//            delete = view.findViewById(R.id.delete)
//            update = view.findViewById(R.id.update)
//        }
//
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.user_show, parent, false)
//        return UserHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return lst.size
//    }
//
//    override fun onBindViewHolder(holder: UserHolder, position: Int) {
//
//        var current = lst[position]
//
//        holder.fullname.text = current.FirstName
//        holder.username.text = current.Username
//
//        Glide.with(context).load("${RetrofitBuilder.BASE_URL}images/${current.Profile}").into(holder.img)
//
//        holder.delete.setOnClickListener() {
//
//            CoroutineScope(Dispatchers.IO).launch {
//
//                val ur = UserRepo()
//                val response = ur.delete(current._id.toString())
//                UserDB.getInstance(context).getDAO().delete(current)
//                lst.removeAt(position)
//                if (response.success == true) {
//                    withContext(Dispatchers.Main) {
//                        notifyDataSetChanged()
//                        Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(context, "Gadbad Vayo", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                }
//
//
//            }
//
//        }
//
//        holder.update.setOnClickListener() {
//
//
//            var dialog = Dialog(context, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
//            dialog.setContentView(R.layout.alert_update)
//            dialog.show()
//            dialog.setCancelable(true)
//            val gallery_code = 0
//            val camera_code = 1
//            var image: String? = null
//            var etFname: EditText = dialog.findViewById(R.id.etFname)
//            var etLname: EditText = dialog.findViewById(R.id.etLname)
//            var etPhone: EditText = dialog.findViewById(R.id.etPhone)
//            var etUsername: EditText = dialog.findViewById(R.id.etUsername)
//            var etPassword: EditText = dialog.findViewById(R.id.etPassword)
//            var etConfirmPassword: EditText = dialog.findViewById(R.id.etCPassword)
//            var btnAddStudent: TextView = dialog.findViewById(R.id.btnAddStudent)
//            var login: TextView = dialog.findViewById(R.id.login)
//            var img: ImageView = dialog.findViewById(R.id.logo)
//            val male: RadioButton = dialog.findViewById(R.id.male)
//            val female: RadioButton = dialog.findViewById(R.id.female)
//            val others: RadioButton = dialog.findViewById(R.id.others)
//            var gender = ""
//            val type = "Customer"
//
//            etFname.setText(lst[position].FirstName)
//            etLname.setText(lst[position].Lastname)
//            etPhone.setText(lst[position].PhoneNumber)
//            etPassword.setText(lst[position].Password)
//            etUsername.setText(lst[position].Username)
//            Glide.with(context).load(lst[position].Profile).into(img)
//
//
//            img.setOnClickListener() {
//
//                val m = MainActivity()
//                val popup = PopupMenu(context, img)
//                popup.menuInflater.inflate(R.menu.gallery_camera, popup.menu)
//                popup.setOnMenuItemClickListener { item ->
//                    when (item.itemId) {
//                        R.id.menuCamera ->
//                            m.openCamera()
//                        R.id.menuGallery -> {
//                            m.openGallery()
//                        }
//
//
//                    }
//
//                    true
//                }
//                popup.show()
//            }
//
//
//            btnAddStudent.setOnClickListener() {
//
//
//                val user: User = User(fname = etFname.text.toString(), lname = etLname.text.toString(),  username = etUsername.text.toString(), password = etPassword.text.toString())
//
//
//                lst[position].FirstName = etFname.text.toString()
//                lst[position].Lastname = etLname.text.toString()
//                lst[position].PhoneNumber = etPhone.text.toString()
//                lst[position].Password = etPassword.text.toString()
//                lst[position].Username = etUsername.text.toString()
//
//
//                CoroutineScope(Dispatchers.IO).launch {
//
//
//                    try {
//
//
//                        val ur = UserRepo()
//                        val response = ur.update(current._id!!)
//                        if (response.success == true) {
//                            UserDB.getInstance(context)
//                                    .getDAO()
//                                    .update(lst[position])
//
//                            withContext(Dispatchers.Main) {
//                                notifyDataSetChanged()
//                                Toast.makeText(context, "student data updated", Toast.LENGTH_SHORT)
//                                        .show()
//
//
//                            }
//
//
//                        }
//
//
//                    } catch (ex: Exception) {
//
//                    }
//
//                }
//
//                notifyDataSetChanged()
//                dialog.dismiss()
//
//
//            }
//
//
//        }
//
//
//    }
//
//
//}