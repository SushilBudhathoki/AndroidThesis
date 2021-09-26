package com.xrest.finalassignment.Fragmnet

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xrest.finalassignment.Models.User
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.Retrofit.UserRepo
import com.xrest.finalassignment.UI.Functions
import com.xrest.finalassignment.UI.Login
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class UserFragment : Fragment() {
    val gallery_code = 0
    val camera_code = 1
    var image: String? = null
   lateinit var img:CircleImageView
    var functions:Functions? =null
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_user, container, false)
      functions=Functions(container!!.context) as Functions

      CoroutineScope(Dispatchers.IO).launch {
          try{
              val repo = UserRepo()
              val response = repo.current()
              RetrofitBuilder.user=response.data!!
          }
          catch (ex:java.lang.Exception){

          }
      }


val user = RetrofitBuilder.user!!
val fname:TextView = view.findViewById(R.id.fname)
      val lname:TextView = view.findViewById(R.id.lname)
      val phone:TextView = view.findViewById(R.id.phone)
      val up:TextView = view.findViewById(R.id.up)
      val username:TextView = view.findViewById(R.id.username)
      val logout:TextView = view.findViewById(R.id.logout)

      img = view.findViewById(R.id.profile)
      up.isVisible=false
      fname.text = user.FirstName
      lname.text = user.Lastname
      username.text= user.Username
      phone.text = user.PhoneNumber
       if(user.Profile=="no-img.jpg"){
          Glide.with(container!!.context).load(R.drawable.logo).into(img)

      }
      else{

          Glide.with(container!!.context).load("${RetrofitBuilder.BASE_URL}images/${user.Profile}").into(img)
      }

      img.setOnClickListener(){

          val popup = PopupMenu(requireContext(), img)
          popup.menuInflater.inflate(R.menu.gallery_camera, popup.menu)
          popup.setOnMenuItemClickListener { item ->
              when (item.itemId) {
                  R.id.menuCamera ->
                      openCamera()
                  R.id.menuGallery -> {
                      openGallery()
                  }


              }

              true
          }
          popup.show()
          up.isVisible=true
      }


      logout.setOnClickListener(){
          RetrofitBuilder.token = null
          RetrofitBuilder.user = null
          val prefs = activity?.getSharedPreferences("Saved", AppCompatActivity.MODE_PRIVATE);
          val editor = prefs?.edit()
          if (editor != null) {
              editor.clear()
              editor.apply()
          }
          val intent = Intent(requireContext(), Login::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
          intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
          startActivity(intent);
          activity?.finish();

      }

      up.setOnClickListener(){
          update(image!!,user._id!!)
      }
      return view
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, gallery_code)

    }

    fun openCamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, camera_code)


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == gallery_code && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = requireContext().contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                image = cursor.getString(columnIndex)
                img.setImageBitmap(BitmapFactory.decodeFile(image))
                cursor.close()
            } else if (requestCode == camera_code && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = functions!!.bitmapToFile(imageBitmap, "$timeStamp.jpg")
                image = file!!.absolutePath
                img.setImageBitmap(BitmapFactory.decodeFile(image))
            }
        }

    }
    fun update( image: String,user:String) {
        print(image)
        if (image != null) {
            val file = File(image!!)
            val extension = MimeTypeMap.getFileExtensionFromUrl(image)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            val reqFile =
                RequestBody.create(MediaType.parse(mimeType), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = UserRepo()
                    val response = repository.upload(user!!,body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Details Updated",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val transaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.fl,UserFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
                }
            } catch (ex: Exception) {

                Toast.makeText(requireContext(), ex.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }

}