package com.xrest.finalassignment.Response

import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.Models.User

data class InsertResponse(
                     val status:Boolean?=null,
                     val data: Person?=null

) {
}