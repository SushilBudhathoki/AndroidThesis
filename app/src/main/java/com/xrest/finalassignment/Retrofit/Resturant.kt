package com.xrest.finalassignment.Retrofit

import androidx.room.Delete
import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.Response.ResturantResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Resturant {

    @POST("/addResturant")
    suspend fun addResturant(@Header("Authorization")token:String,@Body resturant: Restuarant):Response<ResturantResponse>

    @Multipart
    @PUT("/upresturant/{id}")
    suspend fun updateResturant(@Header("Authorization")token:String,@Part file: MultipartBody.Part,@Path("id")id:String):Response<ResturantResponse>



    @DELETE("/rest/{id}")
    suspend fun deleteRest(@Path("id")id:String):Response<ResturantResponse>


}