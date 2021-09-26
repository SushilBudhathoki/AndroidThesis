package com.xrest.wearos


import okhttp3.MultipartBody

class FoodRepo : myApiRequest() {


    val api = RetrofitBuilder.buildServices(ApiFood::class.java)



    suspend fun getBooking(): BookingResponse {

        return apiRequest {

        api.getBooking(RetrofitBuilder.token!!)
        }
    }


    suspend fun delete( objectId: String): DeleteResponse {
        return apiRequest {
            api.deleteBooking(RetrofitBuilder.token!!, objectId)
        }
    }

    //Restaurant

    suspend fun getFood(): FoodResponse {
        return apiRequest {
            api.getFood()
        }
    }

suspend fun updateBooking(pid:String,b:Bookings):orderResponse{
    return apiRequest {
        api.update(RetrofitBuilder.token!!,pid,b)
    }

}
    suspend fun book(pid: String,Qty: Bookings): DeleteResponse {
        return apiRequest {
            api.book(RetrofitBuilder.token!!, pid,Qty)
        }
    }





}