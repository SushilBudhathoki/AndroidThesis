package com.xrest.wearos


import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiC {


    @FormUrlEncoded
    @POST("/login")
    suspend fun Login(@Field("username") username: String,
                      @Field("password") password: String): Response<LoginResponse>


}