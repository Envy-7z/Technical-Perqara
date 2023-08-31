package com.wisnu.technicalassessmentlimaperqara.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisnu.technicalassessmentlimaperqara.data.models.FavEntity
import com.wisnu.technicalassessmentlimaperqara.databinding.ContentMainBinding

class FavoriteAdapter(private val listFavorite: List<FavEntity>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(binding: ContentMainBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvName: TextView = binding.tvTitle
        val ivPhoto: ImageView = binding.ivPoster
        val tvRelease: TextView = binding.tvDate
        val tvRating: TextView = binding.tvRate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ContentMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.text = listFavorite[position].name
        holder.tvRelease.text = "Release date ${listFavorite[position].released}"
        holder.tvRating.text = listFavorite[position].rating.toString()

        Glide.with(holder.itemView.context).load(listFavorite[position].background_image).into(holder.ivPhoto)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[holder.absoluteAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listFavorite.size

    interface OnItemClickCallback {
        fun onItemClicked(data: FavEntity)
    }
}