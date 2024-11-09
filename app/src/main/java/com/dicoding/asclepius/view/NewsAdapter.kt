package com.dicoding.asclepius.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.Articles
import com.dicoding.asclepius.databinding.ListNewsBinding

class NewsAdapter: ListAdapter<Articles, NewsAdapter.ViewHolder>(DIFF_CALLBACK ) {

    class ViewHolder(private val binding: ListNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles) {
            Glide.with(binding.root.context)
                .load(item.urlToImage)
                .into(binding.newsImage)

            binding.newsTitle.text = item.title
            binding.newsDescription.text = item.description
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Articles>(){

            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}