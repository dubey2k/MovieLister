package com.example.myapplication.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.IMAGE_BASE
import com.example.myapplication.databinding.MainItemBinding
import com.example.myapplication.data.models.Movie


class MainAdapter: PagingDataAdapter<Movie, MainAdapter.mainVH>(DiffUtilCallBack()) {

    inner class mainVH(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mainVH {
        val view = MainItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        )
        return mainVH(view)
    }

    override fun onBindViewHolder(holder: mainVH, position: Int) {
        holder.itemView.apply {
            holder.binding.titleItem.text = getItem(position)?.original_title
            holder.binding.descItem.text = getItem(position)?.overview
            holder.binding.posterImg
            Glide.with(this).load(IMAGE_BASE + getItem(position)?.poster_path)
                .into(holder.binding.posterImg)
        }
    }
}