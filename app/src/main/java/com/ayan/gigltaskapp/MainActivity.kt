package com.ayan.gigltaskapp

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.ayan.gigltaskapp.adapters.MainAdapter
import com.ayan.gigltaskapp.interfaces.ListImageLoadListener
import com.ayan.gigltaskapp.models.ListData
import com.ayan.gigltaskapp.models.Photo
import com.ayan.gigltaskapp.viewModels.MainViewModel
import com.ayan.gigltaskapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ListImageLoadListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        GlobalScope.launch(Dispatchers.IO) {
            if (isInternetAvailable(applicationContext)) viewModel.fetchData(5, 15)
            else viewModel.fetchDataFromLocal()
        }
    }

    private fun setObservers() {
        viewModel.photosLiveData.observe(this) {
            binding.mainRcv.layoutManager = LinearLayoutManager(this)
            val mainList = getMainList(it)
            binding.mainRcv.adapter = MainAdapter(mainList, this)
            binding.pBar.isVisible = false
        }
        viewModel.toastLiveData.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMainList(photoList: List<Photo>?): ArrayList<ListData?> {
        return if (photoList?.size!! > 5) return arrayListOf(
            ListData(photoList.subList(0, 5), 2), // top 5 should go to horizontal views
            ListData(photoList.subList(5, photoList.size), 1),
            ListData(photoList.subList(0, 5).reversed(), 2)
        ) else arrayListOf(
            ListData(
                photoList.subList(0, photoList.size / 2),
                2
            ), // top 5 should go to horizontal views
            ListData(photoList.subList((photoList.size / 2), photoList.size), 1)
        )
    }

    override fun imageLoaded(position: Int, bitmap: Bitmap, photo: Photo) {
        viewModel.savePhotoToLocalDb(position, bitmap, photo)
    }

    private fun isInternetAvailable(context: Context): Boolean =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected == true
}