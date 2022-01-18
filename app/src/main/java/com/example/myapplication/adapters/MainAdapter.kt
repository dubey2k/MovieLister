package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.IMAGE_BASE
import com.example.myapplication.databinding.MainItemBinding
import com.example.myapplication.models.Movie


class MainAdapter(
) : RecyclerView.Adapter<MainAdapter.mainVH>() {
    inner class mainVH(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

    private  val diffUtil = object : DiffUtil.ItemCallback<Movie>() {
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffUtil)
    var items : List<Movie> get() = differ.currentList
    set(value) {differ.submitList(value)}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mainVH {
        val view = MainItemBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.main_item,parent,false));
        return mainVH(view)
    }

    override fun onBindViewHolder(holder: mainVH, position: Int) {
        holder.itemView.apply {
            holder.binding.titleItem.text = items[position].original_title
            holder.binding.descItem.text = items[position].overview
            holder.binding.posterImg
            Glide.with(this).load(IMAGE_BASE + items[position].poster_path).into(holder.binding.posterImg)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}