package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri: Uri? = intent.getParcelableExtra(URI)
        val result = intent.getStringExtra(RESULT)

        binding.resultText.text = result
        binding.resultImage.setImageURI(uri)
    }

    companion object{
        const val RESULT = "result"
        const val URI = "uri"
    }

}