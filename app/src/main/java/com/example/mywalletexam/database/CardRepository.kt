package com.example.mywalletexam.database

import android.app.Application
import com.example.mywalletexam.manager.RoomManager
import com.example.mywalletexam.model.Card

class CardRepository(application: Application) {

    private val dp = RoomManager.getInstance(application)
    private var cardDao = dp.cardDao()

    fun saveCard(card: Card) {
        cardDao.saveCard(card)
    }

    fun getAllSavedCard(): List<Card> {
        return cardDao.getAllSavedCard()
    }

    fun changeIsAdded(id: Int, isAdded: Boolean) {
        cardDao.changeIsAdded(id, isAdded)
    }

}