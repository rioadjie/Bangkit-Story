package com.md12.rio.bangkitstory.data.remote.api

import com.md12.rio.bangkitstory.data.remote.response.login.ResponseLogin
import com.md12.rio.bangkitstory.data.remote.response.register.ResponseRegister
import com.md12.rio.bangkitstory.data.remote.response.story.ResponseStory
import com.md12.rio.bangkitstory.data.remote.response.upload.ResponseUploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseRegister

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : ResponseLogin

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") auth: String
    ) : ResponseStory

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : ResponseUploadStory


}