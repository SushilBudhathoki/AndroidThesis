package com.xrest.finalassignment.Retrofit

import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.Response.ResturantResponse
import okhttp3.MultipartBody

class ResturantRepo:myApiRequest() {

    val api = RetrofitBuilder.buildServices(Resturant::class.java)


    suspend fun addRest(resturant:Restuarant):ResturantResponse{

        return apiRequest {
            api.addResturant(RetrofitBuilder.token!!,resturant)
        }

    }

    suspend fun deleteRest(id:String):ResturantResponse{
        return apiRequest {

            api.deleteRest(id)
        }
    }


    suspend fun updateResturant(body:MultipartBody.Part,id:String):ResturantResponse{
        return apiRequest {
            api.updateResturant(RetrofitBuilder.token!!,body,id)
        }
    }






}