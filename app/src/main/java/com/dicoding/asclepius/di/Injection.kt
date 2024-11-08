package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.HistoryDao
import com.dicoding.asclepius.data.local.room.HistoryDatabase
import com.dicoding.asclepius.repository.HistoryRepository

object Injection {
    fun provideRepository(context: Context): HistoryRepository{
        val database = HistoryDatabase.getInstance(context)
        val historyDao = database.historyDao()
        return HistoryRepository(historyDao)
    }
}