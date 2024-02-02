package com.ayan.gigltaskapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    val id: Int,
    val description: String,
    val title: String,
    val url: String,
    val user: Int,
    var imageBytes: ByteArray? = null
)