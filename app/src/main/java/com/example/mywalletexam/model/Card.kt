package com.example.mywalletexam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class Card(
    val card_number: String,
    val cvv: String,
    val expiration_date: String,
    val holder_name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    var isAdded: Boolean? = false
)