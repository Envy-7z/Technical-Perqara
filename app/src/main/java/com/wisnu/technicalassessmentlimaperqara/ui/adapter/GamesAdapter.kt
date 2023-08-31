package com.wisnu.technicalassessmentlimaperqara.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wisnu.technicalassessmentlimaperqara.R
import com.wisnu.technicalassessmentlimaperqara.data.models.Result
import com.wisnu.technicalassessmentlimaperqara.databinding.ContentMainBinding

class GamesAdapter(
    private val callback: ((Result) -> Unit)?
) : PagingDataAdapter<Result, GamesAdapter.MainViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = ContentMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                callback?.invoke(data)
            }
        }
    }

    class MainViewHolder(private val binding: ContentMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Result) {
            with(binding) {
                try {
                    Glide.with(itemView.context)
                        .load(item.background_image)
                        .error(R.drawable.baseline_report_gmailerrorred_24)
                        .into(ivPoster)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("wisnu siapa nich","asdasda $item")
                tvTitle.text = item.name
                tvDate.text = "Release date ${item.released}"
                tvRate.text = item.rating.toString()
            }
        }
    }
}

