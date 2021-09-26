package com.xrest.wearos


import okhttp3.MultipartBody

class UserRepo : myApiRequest() {


    val api = RetrofitBuilder.buildServices(ApiC::class.java)


    suspend fun Login(username: String, password: String): LoginResponse {
        return apiRequest {

            api.Login(username, password)
        }

    }



}