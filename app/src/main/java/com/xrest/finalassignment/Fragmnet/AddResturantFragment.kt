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
import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.FoodRepo
import com.xrest.finalassignment.Retrofit.ResturantRepo
import com.xrest.finalassignment.RoomDatabase.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

class AddResturantFragment : Fragment() {

    private val gallery_code = 0
    private val camera_code = 1
    var image: String? = null
    private lateinit var images: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_add_resturant, container, false)
        images = view.findViewById(R.id.image)

        val name:EditText = view.findViewById(R.id.name)
        val rating:EditText = view.findViewById(R.id.rating)
        val address:EditText = view.findViewById(R.id.address)
        val phone:EditText = view.findViewById(R.id.phone)
        val submit: Button = view.findViewById(R.id.submit)

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
        submit.setOnClickListener(){

            if (image != null) {

                var file = File(image!!)
                print(file)
                val extention = MimeTypeMap.getFileExtensionFromUrl(image)
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extention)
                var reqFile = RequestBody.create(MediaType.parse(mimeType), file)
                var body = MultipartBody.Part.createFormData("images", file.name, reqFile)
                CoroutineScope(Dispatchers.IO).launch {


                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Resturant Added", Toast.LENGTH_SHORT).show()

                    }

                        val res = Restuarant(name=name.text.toString(),address = address.text.toString(),phone = phone.text.toString(),rating = rating.text.toString())

                        val ur = ResturantRepo()
                        val response =ur.addRest(res)
                        if (response.success == true) {
                              ur.updateResturant(body,response.data?._id!!)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Resturant Added", Toast.LENGTH_SHORT).show()

                            }
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

}