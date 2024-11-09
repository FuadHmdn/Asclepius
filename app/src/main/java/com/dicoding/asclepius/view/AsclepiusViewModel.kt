package com.dicoding.asclepius.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.History
import com.dicoding.asclepius.data.remote.Articles
import com.dicoding.asclepius.repository.HistoryRepository
import kotlinx.coroutines.launch

class AsclepiusViewModel(private val historyRepository: HistoryRepository): ViewModel() {

    fun insertHistory(history: History){
        viewModelScope.launch {
            historyRepository.historyDao.insert(history)
        }
    }

    fun getAllHistory(): LiveData<List<History>>{
        return historyRepository.historyDao.getAll()
    }

    fun getAllNews(): LiveData<List<Articles>>{
        historyRepository.getNews()
        return historyRepository.news
    }

    fun isSucces(): LiveData<Boolean> = historyRepository.isSuccess

    fun setCurrentImageUri(uri: Uri){
        historyRepository.currentImageUri = uri
    }

    fun getUri() = historyRepository.currentImageUri
}