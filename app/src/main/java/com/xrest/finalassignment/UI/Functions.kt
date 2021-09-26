package com.xrest.finalassignment.UI

import com.xrest.finalassignment.Retrofit.UserRepo
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.webkit.MimeTypeMap
import android.widget.Toast
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

class Functions(val context: Context) {


    fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
        print(image)
        if (image != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val file = File(image!!)
                val extension = MimeTypeMap.getFileExtensionFromUrl(image)
                val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
                val reqFile =
                        RequestBody.create(MediaType.parse(mimeType), file)
                val body =
                        MultipartBody.Part.createFormData("file", file.name, reqFile)
                try {
                    val repository = UserRepo()
                    val response = repository.upload(id, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "User Uploded Added", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}