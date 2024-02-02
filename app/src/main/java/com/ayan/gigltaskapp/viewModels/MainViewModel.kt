package com.ayan.gigltaskapp.viewModels

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ayan.gigltaskapp.database.DatabaseBuilder
import com.ayan.gigltaskapp.models.Photo
import com.ayan.gigltaskapp.services.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class MainViewModel(context: Application) : AndroidViewModel(context) {

    private val apiService = RetrofitBuilder.apiService

    //for photos
    private val _photosLiveData = MutableLiveData<List<Photo>>()
    val photosLiveData: LiveData<List<Photo>> get() = _photosLiveData

    //for toast
    private val _toastLiveData = MutableLiveData<String>()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    private val database = DatabaseBuilder.getInstance(context)

    suspend fun fetchData(offset: Int, limit: Int) {
        try {
            val responseData = apiService.getPhotos(offset, limit)
            if (responseData.body() != null) {
                database.photoDao().deleteAllPhotos() //deleting all previous photos
                _photosLiveData.postValue(responseData.body()!!.photos)
            } else {
                //have to show an toast
                _toastLiveData.postValue("error: response body is null!")
            }
        } catch (e: Exception) {
            _toastLiveData.postValue("error: ${e.message}")
        }
    }

    suspend fun fetchDataFromLocal() {
        try {
            val responseList = database.photoDao().getAllPhotos()
            _photosLiveData.postValue(responseList.sortedBy { it.id })
        } catch (e: Exception) {
            _toastLiveData.postValue("error: ${e.message}")
        }
    }

    fun savePhotoToLocalDb(position: Int, bitmap: Bitmap, photo: Photo) {
        photo.imageBytes = convertBitmapToByteArray(bitmap)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                database.photoDao().insertPhoto(photo)
            } catch (e: Exception) {
                _toastLiveData.postValue("error: ${e.message}")
            }

        }
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}