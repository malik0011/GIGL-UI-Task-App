package com.ayan.gigltaskapp.interfaces

import android.graphics.Bitmap
import com.ayan.gigltaskapp.models.Photo

interface ListImageLoadListener {
    fun imageLoaded(position: Int, bitmap: Bitmap, photo: Photo)
}