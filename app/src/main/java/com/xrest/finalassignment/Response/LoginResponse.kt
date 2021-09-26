package com.xrest.finalassignment.Response

import com.xrest.finalassignment.Class.Person
import com.xrest.finalassignment.Models.User

data class LoginResponse(
        val status: Boolean? = null,
        val token: String? = null,
        val data: Person? = null

) {
}