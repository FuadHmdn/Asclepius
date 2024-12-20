package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.ResultActivity.Companion.RESULT
import com.dicoding.asclepius.view.ResultActivity.Companion.URI
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel: AsclepiusViewModel by viewModels {
        factory
    }

    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
        binding.historyButton.setOnClickListener { moveToHistory() }
        binding.newsButton.setOnClickListener { moveToNews() }
        currentUri = viewModel.getUri()

        currentUri?.let {
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun startGallery() {
        laucherGaleryActivity.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        binding.previewImageView.setImageURI(null)
        binding.previewImageView.setImageURI(viewModel.getUri())
    }

    private fun analyzeImage() {
        if (currentUri != null) {
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
                        Log.e("ImageClassifierError", error)
                    }

                    override fun onResults(result: List<Classifications>?) {
                        result?.let {
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val sortedCategories =
                                    it[0].categories.sortedByDescending { it.score }
                                val displayResult =
                                    sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                                currentUri?.let { uri -> moveToResult(displayResult, uri) }
                            } else {
                                Toast.makeText(this@MainActivity,
                                    getString(R.string.gagal), Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                }
            )

            currentUri?.let { uri ->
                imageClassifierHelper.classifyStaticImage(uri)
            }
        } else {
            Toast.makeText(this@MainActivity,
                getString(R.string.silahkan_pilih_gambar_terebih_dahulu), Toast.LENGTH_LONG).show()
        }
    }

    private fun moveToResult(result: String, currentUri: Uri) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(RESULT, result)
        intent.putExtra(URI, currentUri)
        startActivity(intent)
    }

    private fun moveToHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun moveToNews() {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val laucherGaleryActivity = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->
        if ( uri != null) {
            startCrop(uri)
        } else {
            showToast(NO_PHOTO)
        }
    }

    private fun startCrop(sourceUri: Uri) {
        val timestamp = System.currentTimeMillis()
        val destinationUri = Uri.fromFile(File(cacheDir, "croppedImage_$timestamp.jpg"))

        UCrop.of(sourceUri, destinationUri)
            .withMaxResultSize(1080, 1080)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                viewModel.setCurrentImageUri(it)
                currentUri = it
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Toast.makeText(this, "Crop error: ${cropError?.message}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val NO_PHOTO = "No photo selected"
    }
}