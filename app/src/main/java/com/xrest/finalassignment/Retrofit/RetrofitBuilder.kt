package com.xrest.finalassignment.Retrofit

import com.google.android.gms.maps.model.LatLng
import com.xrest.finalassignment.Class.Person
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {


    const val BASE_URL = "http://192.168.0.10:3000/"
//    const val BASE_URL = "http://10.0.2.2:3000/"
//    const val BASE_URL = "http://localhost:3000/"
    var user: Person? = null
    var token: String? = null
    var online: Boolean? = null
    var rid: String? = null
    var latlng = LatLng(2.3,2.3)


    val okhttp = OkHttpClient.Builder()
    val retroFitBuilder = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okhttp.build())


    val retrofit = retroFitBuilder.build()

    //generic functions
    fun <T> buildServices(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }


}