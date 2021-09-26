package com.xrest.finalassignment.Retrofit

import com.xrest.finalassignment.Models.User
import com.xrest.finalassignment.Response.*
import okhttp3.MultipartBody

class UserRepo : myApiRequest() {


    val api = RetrofitBuilder.buildServices(ApiC::class.java)


    suspend fun Login(username: String, password: String): LoginResponse {
        return apiRequest {

            api.Login(username, password)
        }

    }


    suspend fun register(user: User): InsertResponse {
        return apiRequest {
            api.Register(user)
        }

    }

    suspend fun upload(id: String, body: MultipartBody.Part): ImageResponse {
        return apiRequest {
            api.addPhoto(id, body)
        }
    }

    suspend fun getData(): GetDataResponse {
        return apiRequest {
            api.getData()
        }
    }

    suspend fun delete(id: String): DeleteResponse {
        return apiRequest {
            api.deleteUser(id)
        }
    }

    suspend fun update(body:MultipartBody.Part,user:User): DeleteResponse {
        return apiRequest {
            api.updateUser(RetrofitBuilder.token!!,body,user)
        }
    }

    suspend fun current(): InsertResponse {
        return apiRequest {
            api.userDetail(RetrofitBuilder.token!!)
        }
    }
}