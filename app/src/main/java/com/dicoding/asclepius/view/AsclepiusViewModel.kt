package com.dicoding.asclepius.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.History
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
}