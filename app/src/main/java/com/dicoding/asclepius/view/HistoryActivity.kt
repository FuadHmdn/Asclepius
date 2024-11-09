package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistoryBinding
    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val asclepiusViewModel: AsclepiusViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        val adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter

        asclepiusViewModel.getAllHistory().observe(this){ history ->
            if (history.isNotEmpty()) {
                adapter.submitList(history)
            } else {
                adapter.submitList(emptyList())
            }
        }
    }

}