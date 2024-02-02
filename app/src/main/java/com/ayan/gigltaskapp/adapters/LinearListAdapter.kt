package com.ayan.gigltaskapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayan.gigltaskapp.interfaces.ListImageLoadListener
import com.ayan.gigltaskapp.models.Photo
import com.ayan.gigltaskapp.databinding.LinearItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class LinearListAdapter(private val items: List<Photo>, val listener: ListImageLoadListener) :
    RecyclerView.Adapter<LinearListAdapter.LinearViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LinearItemBinding.inflate(inflater, parent, false)
        return LinearViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LinearViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class LinearViewHolder(private val binding: LinearItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Photo) {
            if (item.imageBytes != null) {
                Glide.with(binding.root.context)
                    .load(item.imageBytes)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)) //better performance
                    .into(binding.ivPhoto)
            } else {
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(item.url)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            // Display the thumbnail in your ImageView
                            binding.ivPhoto.setImageBitmap(resource)

                            // Save the thumbnail to a local file
                            listener.imageLoaded(position, resource, item)
                        }
                    })
            }
        }
    }
}