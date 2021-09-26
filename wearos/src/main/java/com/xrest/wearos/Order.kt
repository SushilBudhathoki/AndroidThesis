package com.xrest.wearos

data class Order(
        val _id :String?=null,
        val UserId:String?=null,
        val ProductId:MutableList<products>?=null,
        val totalAmount:String?=null,
        val orderDate:String?=null,
        val paidThrough:String?=null,
        val paymentResponse:String?=null,
        val orderStatus:Boolean?=null,
        val DeliveredStatus:Boolean?=null,
        val EmployeeId:String?=null,
        val lat:String?=null,
        val lng:String?=null




)