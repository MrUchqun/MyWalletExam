package com.example.mywalletexam.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mywalletexam.model.Card

@Dao
interface CardDao {

    @Insert
    fun saveCard(pins: Card)

    @Query("SELECT * FROM card_table")
    fun getAllSavedCard(): List<Card>

    @Query("UPDATE card_table SET isAdded=:isAdded WHERE id=:id")
    fun changeIsAdded(id: Int, isAdded: Boolean)
}