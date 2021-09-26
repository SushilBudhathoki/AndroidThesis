package com.xrest.wearos


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiFood {


    @GET("/booking/show")
    suspend fun getBooking(
            @Header("authorization") token: String
    ): Response<BookingResponse>

    @GET("/food/show")
    suspend fun getFood(): Response<FoodResponse>


    @PUT("/delete/booking/{oid}")
    suspend fun deleteBooking(@Header("authorization") token: String, @Path("oid") objectId: String): Response<DeleteResponse>

    @PUT("/updatebooking/{pid}")
    suspend fun update(@Header("authorization") token: String, @Path("pid") pid: String,@Body body:Bookings): Response<orderResponse>

    @POST("booking/{pid}")
    suspend fun book(
        @Header("Authorization") token: String,
        @Path("pid") pid: String,
        @Body body:Bookings


    ): Response<DeleteResponse>

}

