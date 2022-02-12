package com.example.myapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.IMAGE_BASE
import com.example.myapplication.data.models.Movie
import com.example.myapplication.databinding.MovieListItemBinding


class MainAdapter(val obj: MovieItemClick): PagingDataAdapter<Movie, MainAdapter.mainVH>(DiffUtilCallBack()) {

    interface MovieItemClick{
        fun onClick( movie: Movie)
    }
    inner class mainVH(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mainVH {
        val view = MovieListItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
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
        holder.itemView.setOnClickListener {
            obj.onClick(getItem(position)!!)
        }
    }
}