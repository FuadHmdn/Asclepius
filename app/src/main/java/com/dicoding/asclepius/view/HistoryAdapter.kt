package com.dicoding.asclepius.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.asclepius.data.local.entity.History
import com.dicoding.asclepius.databinding.ListHistoryBinding

class HistoryAdapter: ListAdapter<History, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ListHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: History) {
            val uri = Uri.parse(item.uri)
            binding.skinImage.setImageURI(uri)
            binding.resultAnalysis.text = item.result
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>(){

            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}