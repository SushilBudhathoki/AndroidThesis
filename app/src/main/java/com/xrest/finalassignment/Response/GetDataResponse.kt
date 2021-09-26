package com.xrest.finalassignment.Response

import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.Models.User

data class GetDataResponse(
        val success:Boolean?=null,
        val data:MutableList<Person>?=null

) {
}