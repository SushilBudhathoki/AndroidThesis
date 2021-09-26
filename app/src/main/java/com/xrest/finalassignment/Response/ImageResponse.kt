package com.xrest.finalassignment.Response

import com.xrest.finalassignment.Class.Food
import com.xrest.finalassignment.Class.Restuarant
import com.xrest.finalassignment.Models.User

data class ImageResponse(
        val success:Boolean?=null,
        val data: User?=null,
        val food:Food?=null

)

data class RestResp(
        val success:Boolean?=null,
        val data:MutableList<Restuarant>
)