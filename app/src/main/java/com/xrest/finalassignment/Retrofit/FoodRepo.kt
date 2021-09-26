package com.xrest.finalassignment.Retrofit

import androidx.room.Delete
import com.xrest.finalassignment.Class.Bookings
import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.UserLat
import com.xrest.finalassignment.Response.*
import okhttp3.MultipartBody

class FoodRepo : myApiRequest() {


    val api = RetrofitBuilder.buildServices(ApiFood::class.java)

//Food routes
    suspend fun insertFood(food: Food, rid: String): FoodResponse {
        return apiRequest {
            api.insertFood(food, rid,RetrofitBuilder.token!!)
        }
    }

    suspend fun getFood(): FoodResponse {
        return apiRequest {
            api.getFood()
        }
    }

    suspend fun uploadw(id: String, body: MultipartBody.Part): ImageResponse {
        return apiRequest {
            api.addPhotow(id, body)
        }
    }

    //booking routes

    suspend fun getBooking(): BookingResponse {

        return apiRequest {

        api.getBooking(RetrofitBuilder.token!!)
        }
    }

    suspend fun getFoodId(id: String): BookFoodResponse {
        return apiRequest {
            api.show(id)
        }

    }

    suspend fun book(pid: String,Qty: Bookings): DeleteResponse {
        return apiRequest {
            api.book(RetrofitBuilder.token!!, pid,Qty)
        }
    }

    suspend fun delete( objectId: String): DeleteResponse {
        return apiRequest {
            api.deleteBooking(RetrofitBuilder.token!!, objectId)
        }
    }

    //Restaurant

    suspend fun getRest(): RestResp {
        return apiRequest {
            api.getResturant()
        }
    }

suspend fun updateBooking(pid:String,b:Bookings):orderResponse{
    return apiRequest {
        api.update(RetrofitBuilder.token!!,pid,b)
    }

}
    //order
    suspend fun UserOrder(lat:UserLat):orderResponse
    {
        return apiRequest {
            api.UserOder(RetrofitBuilder.token!!,lat)
        }
    }

    suspend fun getAllUserOrder():orderDataResponse{
        return apiRequest {
            api.getAllUserOrder(RetrofitBuilder.token!!)
        }
    }
    suspend fun cancelOrder(id:String):DeleteResponse{
        return apiRequest {
            api.cancelOrder(id)
        }
    }

    //admin order
    suspend fun getAllOrders():orderDataResponse{
        return apiRequest {
            api.getAllOrders(RetrofitBuilder.token!!)
        }
    }

    suspend fun unApproved():orderDataResponse{
       return apiRequest {
           api.getUnApprovedOrders(RetrofitBuilder.token!!)
       }

    }
    //orderid
    suspend fun approve(id:String):InsertResponse{
        return apiRequest {
            api.acceptOrder(RetrofitBuilder.token!!,id)
        }
    }


suspend fun  Deliver(orderid:String):DeleteResponse{
    return apiRequest {
        api.Deliver(orderid,RetrofitBuilder.token!!)
    }
}
suspend fun unDelivered():orderDataResponse{
    return apiRequest {
        api.unDelivered(RetrofitBuilder.token!!)
    }
}
    suspend fun search(name:String?):FoodResponse{
        return apiRequest {
            api.search(name)
        }
    }

    suspend fun emp():orderDataResponse{
        return apiRequest {
            api.emp(RetrofitBuilder.token!!)
        }
    }




}