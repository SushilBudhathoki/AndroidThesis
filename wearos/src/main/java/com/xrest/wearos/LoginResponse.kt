package com.xrest.wearos



data class LoginResponse(
        val status: Boolean? = null,
        val token: String? = null,
        val data: Person? = null

) {
}