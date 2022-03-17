package com.example.mywalletexam.network.service

import com.example.mywalletexam.model.Card
import retrofit2.Call
import retrofit2.http.*

interface CardService {

    @GET("card")
    fun getAllCards(): Call<ArrayList<Card>>

    @POST("card")
    fun addCard(@Body card: Card): Call<Card>

}