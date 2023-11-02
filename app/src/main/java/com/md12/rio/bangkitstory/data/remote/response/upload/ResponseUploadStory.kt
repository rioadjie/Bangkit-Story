package com.md12.rio.bangkitstory.data.remote.response.upload

import com.google.gson.annotations.SerializedName

data class ResponseUploadStory(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
