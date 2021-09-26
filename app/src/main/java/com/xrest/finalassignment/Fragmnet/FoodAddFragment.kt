package com.xrest.finalassignment.Fragmnet

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.RetrofitBuilder
import com.xrest.finalassignment.RoomDatabase.UserDB
import com.xrest.finalassignment.UI.Dashboard
import com.xrest.finalassignment.UI.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FoodAddFragment() : Fragment() {

    private val gallery_code = 0
    private val camera_code = 1
    var image: String? = null

    private lateinit var images: ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_add, container, false)
        val functions = Functions(container!!.context)
        var foodName: EditText = view.findViewById(R.id.name)
        var description: EditText = view.findViewById(R.id.description)
        var rating: EditText = view.findViewById(R.id.rating)
        var price: EditText = view.findViewById(R.id.price)
        var time: EditText = view.findViewById(R.id.time)
        images = view.findViewById(R.id.image)


        var submit: Button = view.findViewById(R.id.submit)
        images.setOnClickListener() {
            val popup = PopupMenu(container!!.context, images)
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
        }
        submit.setOnClickListener() {

            CoroutineScope(Dispatchers.IO).launch {
                val ur = FoodRepo()
                val food = Food(Name = foodName.text.toString(), Description = description.text.toString(), Rating = rating.text.toString(), Price = price.text.toString(), time = time.text.toString())

                val response = ur.insertFood(food, RetrofitBuilder.rid.toString())
                if (response.success == true) {

                    uploadImage(response.food!!.toString(), image.toString())
                    withContext(Main) {
                        Toast.makeText(container.context, "Food Added", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(container!!.context, Dashboard::class.java))

                    }


                }


            }
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
                images.setImageBitmap(BitmapFactory.decodeFile(image))
                cursor.close()
            } else if (requestCode == camera_code && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                image = file!!.absolutePath
                images.setImageBitmap(BitmapFactory.decodeFile(image))
            }
        }

    }


    fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file?.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    fun uploadImage(id: String, image: String) {
        if (image != null) {

            var file = File(image!!)
            val extention = MimeTypeMap.getFileExtensionFromUrl(image)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention)
            var reqFile = RequestBody.create(MediaType.parse(mimeType), file)
            var body = MultipartBody.Part.createFormData("Image", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val ur = FoodRepo()
                    val response = ur.uploadw(id, body)
                    if (response.success == true) {
                        UserDB.getInstance(requireContext()).getFoodDAO().InsertFood(Food(Name = response.food!!.Name, Description = response.food!!.Description, Rating = response.food!!.Rating, Price = response.food!!.Price, Image = response.food!!.Image))

                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Add in Room databse", Toast.LENGTH_SHORT).show()

                        }

                    }

                } catch (ex: Exception) {

                }


            }

        }
    }
}