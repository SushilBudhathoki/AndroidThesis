package com.xrest.finalassignment.Retrofit

import com.xrest.finalassignment.Class.*
import com.xrest.finalassignment.Response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiFood {

    //food
    @POST("/add/{rid}")
    suspend fun insertFood(@Body food: Food, @Path("rid") resturantId: String,@Header("Authorization") token:String): Response<FoodResponse>

    @GET("/food/show")
    suspend fun getFood(): Response<FoodResponse>

    @Multipart
    @PUT("/food/photo/{id}")
    suspend fun addPhotow(
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>

    //booking

    @GET("/food/show/{id}")
    suspend fun show(
            @Path("id") id: String
    ): Response<BookFoodResponse>

    @GET("/booking/show")
    suspend fun getBooking(
            @Header("authorization") token: String
    ): Response<BookingResponse>


    @POST("booking/{pid}")
    suspend fun book(
            @Header("Authorization") token: String,
            @Path("pid") pid: String,
            @Body body:Bookings


    ): Response<DeleteResponse>

    @PUT("/delete/booking/{oid}")
    suspend fun deleteBooking(@Header("authorization") token: String, @Path("oid") objectId: String): Response<DeleteResponse>

    @PUT("/updatebooking/{pid}")
    suspend fun update(@Header("authorization") token: String, @Path("pid") pid: String,@Body body:Bookings): Response<orderResponse>

    //Restuarant routes
    @GET("/getRest")
    suspend fun getResturant(): Response<RestResp>

    //orders
    //user routes
    @POST("/order")
    suspend fun UserOder(@Header("Authorization") token: String?,@Body lat:UserLat): Response<orderResponse>

    @GET("/allOrder")
    suspend fun getAllUserOrder(@Header("Authorization")token:String?): Response<orderDataResponse>

    @DELETE("/cancelOrder/{uid}")
    suspend fun cancelOrder(@Path("uid") uid: String):Response<DeleteResponse>

    //admin routes
    @GET("/allOrders")
    suspend fun getAllOrders(@Header("Authorization") token:String): Response<orderDataResponse>

    @GET("/unApproved")
    suspend fun getUnApprovedOrders(
        @Header("Authorization") token:String
    ): Response<orderDataResponse>

    @PUT("/approve/{oid}")
    suspend fun acceptOrder(@Header("Authorization")token:String,@Path("oid") id: String?): Response<InsertResponse>


    //for employees to accept the deliveries
    @PUT("/Acceptdelivery/{orderid}")
    suspend fun Deliver(@Path("orderid") orderid: String, @Header("authorization") token: String): Response<DeleteResponse>

    @GET("unDelivered")
    suspend fun unDelivered(@Header("authorization")token: String): Response<orderDataResponse>

    @GET("/search/{name}")
    suspend fun search(@Path("name") name:String?):Response<FoodResponse>

    @GET("/emp")
    suspend fun emp(@Header("Authorization") token:String): Response<orderDataResponse>

}

