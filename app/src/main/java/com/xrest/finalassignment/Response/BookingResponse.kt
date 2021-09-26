package com.xrest.finalassignment.Response

import com.xrest.finalassignment.Class.Booking

data class BookingResponse(
        val status:Boolean?=null,
        val data:Booking?=null

) {
}