package com.example.mywalletexam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.mywalletexam.R
import com.example.mywalletexam.database.CardRepository
import com.example.mywalletexam.model.Card
import com.example.mywalletexam.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardAddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_add)
        initViews()
    }

    private fun initViews() {
        val etCardNumber: EditText = findViewById(R.id.et_card_number)
        val etDateMonth: EditText = findViewById(R.id.et_date_month)
        val etDateYear: EditText = findViewById(R.id.et_date_year)
        val etCVV: EditText = findViewById(R.id.et_cvv)
        val etHolderName: EditText = findViewById(R.id.et_holder_name)
        val btnAddCard: Button = findViewById(R.id.btn_add_card)

        btnAddCard.setOnClickListener {
            val card = Card(
                etCardNumber.text.toString(),
                etCVV.text.toString(),
                etDateMonth.text.toString() + "/" + etDateYear.text.toString(),
                etHolderName.text.toString(), 1
            )
            setResult(RESULT_OK, Intent().putExtra("newCard", Gson().toJson(card)))
            finish()
        }
    }
}