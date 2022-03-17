package com.example.mywalletexam.manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mywalletexam.database.CardDao
import com.example.mywalletexam.model.Card

@Database(entities = [Card::class], version = 3)
abstract class RoomManager : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {

        private var INSTANCE: RoomManager? = null

        @Synchronized
        fun getInstance(context: Context): RoomManager {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, RoomManager::class.java, "save_card_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }

    }

}