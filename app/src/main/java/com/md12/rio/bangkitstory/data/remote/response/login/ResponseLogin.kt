package com.md12.rio.bangkitstory.data.remote.response.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val result: Result

)