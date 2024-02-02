package com.ayan.gigltaskapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ayan.gigltaskapp.models.Photo

@Dao
interface PhotoDao {
    @Insert
    suspend fun insertPhoto(photo: Photo)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): List<Photo>

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()
}