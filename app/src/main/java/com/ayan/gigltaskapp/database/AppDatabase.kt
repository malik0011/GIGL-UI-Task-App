package com.ayan.gigltaskapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayan.gigltaskapp.models.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}