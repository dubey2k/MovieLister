package com.example.myapplication.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants
import com.example.myapplication.data.models.Movie
import com.example.myapplication.databinding.MainItemBinding

class SearchViewAdapter(var items: MutableList<Movie>) : RecyclerView.Adapter<SearchViewAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = MainItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.main_item,parent,false))
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.apply {
            titleItem.text = items[position].original_title
            descItem.text = items[position].overview
            Glide.with(this.root)
                .load(constants.IMAGE_BASE + items[position].poster_path)
                .placeholder(R.drawable.search_icon)
                .into(holder.binding.posterImg)

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}