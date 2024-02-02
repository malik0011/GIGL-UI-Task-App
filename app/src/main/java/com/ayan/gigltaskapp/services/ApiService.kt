package com.ayan.gigltaskapp.services

import com.ayan.gigltaskapp.models.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("photos")
    suspend fun getPhotos(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ResponseData> // Change Photo to your data model
}