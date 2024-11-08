package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import androidx.activity.viewModels
import com.dicoding.asclepius.data.local.entity.History


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val factory : ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val asclepiusViewModel: AsclepiusViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri: Uri? = intent.getParcelableExtra(URI)
        val result = intent.getStringExtra(RESULT)

        binding.resultText.text = result
        binding.resultImage.setImageURI(uri)

        binding.save.setOnClickListener {
            var history : History? = null
            if (uri != null && result != null) {
                history = History(
                    uri = uri.toString(),
                    result = result
                )
            }
            if (history != null){
                asclepiusViewModel.insertHistory(history)
                Toast.makeText(this, "Berhasil Menyimpan Histori", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    companion object{
        const val RESULT = "result"
        const val URI = "uri"
    }

}