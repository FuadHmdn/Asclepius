package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.History

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{

        @Volatile
        private var instance: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase{
            return instance?: synchronized(this){
                instance?: Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "History.db"
                ).build()
            }
        }
    }

}