package com.dicoding.asclepius.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val asclepiusViewModel: AsclepiusViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvNews.layoutManager = LinearLayoutManager(this)

        val adapter = NewsAdapter()
        binding.rvNews.adapter = adapter

        asclepiusViewModel.getAllNews().observe(this) { news ->
            if (news != null) {
                adapter.submitList(news)
                Log.d("REPONSE", news.toString())
            } else {
                Toast.makeText(this, "DATA KOSONG", Toast.LENGTH_LONG).show()
            }
        }
    }

}