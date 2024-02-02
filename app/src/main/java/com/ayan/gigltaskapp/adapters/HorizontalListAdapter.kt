package com.ayan.gigltaskapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayan.gigltaskapp.interfaces.ListImageLoadListener
import com.ayan.gigltaskapp.models.Photo
import com.ayan.gigltaskapp.databinding.HorizontalItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class HorizontalListAdapter(private val items: List<Photo>, val listener: ListImageLoadListener) :
    RecyclerView.Adapter<HorizontalListAdapter.HorizontalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HorizontalItemBinding.inflate(inflater, parent, false)
        return HorizontalViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HorizontalViewHolder(private val binding: HorizontalItemBinding) :
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