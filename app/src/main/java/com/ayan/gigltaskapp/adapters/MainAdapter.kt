package com.ayan.gigltaskapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayan.gigltaskapp.interfaces.ListImageLoadListener
import com.ayan.gigltaskapp.models.ListData
import com.ayan.gigltaskapp.databinding.ListViewBinding

class MainAdapter(private val itemList: ArrayList<ListData?>, val listener: ListImageLoadListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_ONE -> {
                val binding = ListViewBinding.inflate(layoutInflater, parent, false)
                TypeOneViewHolder(binding, listener)
            }

            VIEW_TYPE_TWO -> {
                val binding = ListViewBinding.inflate(layoutInflater, parent, false)
                TypeTwoViewHolder(binding, listener)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeOneViewHolder -> {
                val item = itemList[position]
                // Bind data for TypeOneItem
                holder.bind(item!!)
            }

            is TypeTwoViewHolder -> {
                val item = itemList[position]
                // Bind data for TypeTwoItem
                holder.bind(item!!)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]?.type) {
            1 -> VIEW_TYPE_ONE
            2 -> VIEW_TYPE_TWO
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class TypeOneViewHolder(
        private val binding: ListViewBinding,
        private val listener: ListImageLoadListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListData) {
            //linear rcv
            binding.rcv.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rcv.adapter = LinearListAdapter(item.list, listener)
        }
    }

    class TypeTwoViewHolder(
        private val binding: ListViewBinding,
        val listener: ListImageLoadListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListData) {
            //vertical rcv
            binding.title.isVisible = true
            binding.rcv.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rcv.adapter = HorizontalListAdapter(item.list, listener)
        }
    }

    companion object {
        private const val VIEW_TYPE_ONE = 1
        private const val VIEW_TYPE_TWO = 2
    }
}