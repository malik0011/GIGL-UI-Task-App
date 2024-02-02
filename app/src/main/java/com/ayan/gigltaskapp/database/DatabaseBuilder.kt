package com.ayan.gigltaskapp.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        synchronized(this) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appp_database"
                ).build()
            }
            return INSTANCE!!
        }
    }
}