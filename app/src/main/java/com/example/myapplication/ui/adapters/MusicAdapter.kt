package com.example.myapplication.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.models.Music
import com.example.myapplication.databinding.MovieListItemBinding
import com.example.myapplication.getImgFromUri

class MusicAdapter(val obj: MusicItemClick) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {
    var list: List<Music> = listOf()

    inner class MusicViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = MovieListItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false)
        )
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.itemView.apply {
            holder.binding.titleItem.text = list[position].title
            holder.binding.descItem.text = list[position].albumName
            holder.binding.posterImg
            Glide.with(this)
                .load(getImgFromUri(Uri.parse(list[position].data), holder.itemView.context))
                .into(holder.binding.posterImg)
        }
        holder.itemView.setOnClickListener {
            obj.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface MusicItemClick {
        fun onClick(index: Int)
    }

}