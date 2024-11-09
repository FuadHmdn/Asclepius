package com.dicoding.asclepius.repository

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.data.local.room.HistoryDao
import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.data.remote.Articles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryRepository(
    val historyDao: HistoryDao,
    private val apiService: ApiService
) {

    private var _news = MutableLiveData<List<Articles>>()
    val news get() = _news

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess get() = _isSuccess

    var currentImageUri: Uri? = null

    init {
        getNews()
    }

    fun getNews(){
        _isSuccess.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = apiService.getNews("cancer", "health", "en", "ebf1fea4ff334f11af630f2e5d7048b1")
                _news.postValue(response.articles)

                withContext(Dispatchers.Main) {
                    _isSuccess.value = false
                }

            } catch (e: Exception) {
                Log.e("RESPONSE", e.message.toString())
            }
        }
    }
}