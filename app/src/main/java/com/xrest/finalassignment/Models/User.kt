package com.xrest.finalassignment.Models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class User(

        val _id: String? = null,
        val fname: String? = null,
        val lname: String? = null,

        val phone: String? = null,
        val username: String? = null,
        val password: String? = null,
        val profile: String? = null,
        val type: String? = null) {

}