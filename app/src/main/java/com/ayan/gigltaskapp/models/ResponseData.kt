package com.ayan.gigltaskapp.models

import com.ayan.gigltaskapp.models.Photo

data class ResponseData(
    val limit: Int,
    val message: String,
    val offset: Int,
    val photos: List<Photo>,
    val success: Boolean,
    val total_photos: Int
)