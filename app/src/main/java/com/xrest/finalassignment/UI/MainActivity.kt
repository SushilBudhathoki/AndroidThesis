package com.xrest.finalassignment.UI

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.provider.Settings
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.Models.User
import com.xrest.finalassignment.R
import com.xrest.finalassignment.Retrofit.UserRepo
import com.xrest.finalassignment.RoomDatabase.UserDB
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var etFname: EditText
    private lateinit var etLname: EditText
    private lateinit var etPhone: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAddStudent: TextView
    private lateinit var img: ImageView
    private lateinit var login: TextView
    private val gallery_code = 0
    private val camera_code = 1
    var image: String? = null


        lateinit var executor:Executor
        lateinit var biometric:BiometricPrompt
        val callBack :BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
            }
        }

    val functions = Functions(this@MainActivity)
private lateinit var sensor:Sensor
private lateinit var sensorManager:SensorManager
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()



        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        if(!checkSensor()){
            return
        }
        else{
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }




        btnAddStudent.setOnClickListener() {
            register()

        }
        login.setOnClickListener() {
            startActivity(Intent(this, Login::class.java))
        }
        img.setOnClickListener() {
            val popup = PopupMenu(this, img)
            popup.menuInflater.inflate(R.menu.gallery_camera, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menuCamera -> {
                        openCamera()

                    }

                    R.id.menuGallery -> {
                        openGallery()
                    }


                }

                true
            }
            popup.show()
        }


    }


    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            flag = false
        }
        return flag
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

    fun bind() {

        etFname = findViewById(R.id.etFname)
        etLname = findViewById(R.id.etLname)
        etPhone = findViewById(R.id.etPhone)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etCPassword)
        btnAddStudent = findViewById(R.id.btnAddStudent)
        login = findViewById(R.id.login)
        img = findViewById(R.id.logo)
    }

    fun register() {
        val fname = etFname.text.toString()
        val lname = etLname.text.toString()
        val phone = etPhone.text.toString()
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        val user = User(
            fname = fname,
            lname = lname,
            phone = phone,
            username = username,
            password = password
        )


        if (password != confirmPassword) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
        } else {

            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val userRepo = UserRepo()

                    val response = userRepo.register(user)
                    if (response.status == true) {

                        if (image != null) {

                            uploadImage(response.data!!._id!!, image!!)

                        } else {
                            val person = Person(
                                response.data!!._id!!,
                                fname,
                                lname,

                                phone,
                                username,
                                password,
                                response.data.Profile,
                                response.data.UserType
                            )
                            UserDB.getInstance(this@MainActivity).getDAO().InsertUser(person)
                        }
                        withContext(Dispatchers.Main) {


                            Toast.makeText(this@MainActivity, "User Added", Toast.LENGTH_SHORT)
                                    .show()
                            startActivity(Intent(this@MainActivity, Login::class.java))
                        }

                    }
                }
            } catch (ex: Exception) {


                Toast.makeText(this@MainActivity, "User already exists", Toast.LENGTH_SHORT).show()


            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == gallery_code && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
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
                val file = functions.bitmapToFile(imageBitmap, "$timeStamp.jpg")
                image = file!!.absolutePath
                img.setImageBitmap(BitmapFactory.decodeFile(image))
            }
        }

    }

    fun uploadImage(id: String, image: String) {
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
                    val response = repository.upload(id, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "User Uploded Added",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (ex: Exception) {

                Toast.makeText(this@MainActivity, ex.toString(), Toast.LENGTH_SHORT).show()

            }
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {

        val values = event!!.values[0]


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + getPackageName())
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {


                Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS, 0
                )
                if(values>15000f) {
                    Toast.makeText(this, "15000", Toast.LENGTH_SHORT).show()
                    Settings.System.putInt(
                        contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS, 80
                    )
                }
                else if(values> 30000f){
                    Toast.makeText(this, "30000", Toast.LENGTH_SHORT).show()

                    Settings.System.putInt(
                        contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS, 190
                    )
                }
                else if(values== 40000f){
                    Toast.makeText(this, "40000", Toast.LENGTH_SHORT).show()

                    Settings.System.putInt(
                        contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS, 255
                    )
                }










            }
        }




    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}