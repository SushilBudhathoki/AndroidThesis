package com.xrest.wearos



class FoodResponse(
        val success:Boolean?=null,
        val data:MutableList<Food>?=null,
        val datas:Food?=null,
        val message:String?=null,
val food:String?=null
)


data class orderResponse(
        val success:Boolean?=null

)
data class orderDataResponse(
        val success:Boolean?=null,
        val data: MutableList<Order>?=null,
val total:String?=null
)