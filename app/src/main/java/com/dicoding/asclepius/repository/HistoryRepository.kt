package com.dicoding.asclepius.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.data.local.room.HistoryDao
import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.data.remote.Articles
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryRepository(
    val historyDao: HistoryDao,
    val apiService: ApiService
) {

    private var _news = MutableLiveData<List<Articles>>()
    val news get() = _news

    init {
        getNews()
    }

    fun getNews(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getNews("cancer", "health", "en", "ebf1fea4ff334f11af630f2e5d7048b1")
                _news.postValue(response.articles)
                Log.d("BODY", response.articles.toString())
            } catch (e: Exception) {
                Log.e("RESPONSE", e.message.toString())
            }
        }
    }
}