package com.example.mywalletexam.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mywalletexam.R
import com.example.mywalletexam.model.Card

class CardAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cardList = ArrayList<Card>()

    @SuppressLint("NotifyDataSetChanged")
    fun addCards(cards: ArrayList<Card>) {
        cardList.clear()
        cardList.addAll(cards)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardNumber: TextView = view.findViewById(R.id.tv_card_number)
        val tvHolderName: TextView = view.findViewById(R.id.tv_holder_name)
        val tvExpires: TextView = view.findViewById(R.id.tv_expires)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val card = cardList[position]
        if (holder is CardViewHolder) {
            holder.tvCardNumber.text = card.card_number
            holder.tvHolderName.text = card.holder_name
            holder.tvExpires.text = card.expiration_date
        }
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}