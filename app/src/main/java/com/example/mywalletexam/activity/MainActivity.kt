package com.example.mywalletexam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Adapter
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mywalletexam.R
import com.example.mywalletexam.adapter.CardAdapter
import com.example.mywalletexam.database.CardRepository
import com.example.mywalletexam.model.Card
import com.example.mywalletexam.network.RetrofitHttp
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var cardAdapter: CardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        cardRepository = CardRepository(application)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(this)
        recyclerView.adapter = cardAdapter

        if (isInternetAvailable()) {
            apiCardList()
            addServer()
        } else
            cardAdapter.addCards(ArrayList(cardRepository.getAllSavedCard()))

        val ivAdd: ImageView = findViewById(R.id.iv_add)
        ivAdd.setOnClickListener {
            callCardAddActivity()
        }
    }

    private fun callCardAddActivity() {
        val intent = Intent(this, CardAddActivity::class.java)
        launcher.launch(intent)
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val card: Card =
                    Gson().fromJson(it.data!!.getStringExtra("newCard"), Card::class.java)

                apiAddCard(card)

                cardRepository.saveCard(card)
                cardAdapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
            }
        }

    private fun apiCardList() {
        progressBar.visibility = VISIBLE
        RetrofitHttp.cardService.getAllCards().enqueue(object : Callback<ArrayList<Card>> {
            override fun onResponse(
                call: Call<ArrayList<Card>>,
                response: Response<ArrayList<Card>>
            ) {
                progressBar.visibility = GONE
                cardAdapter.addCards(response.body()!!)
            }

            override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                progressBar.visibility = GONE
                cardAdapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
            }
        })
    }

    private fun apiAddCard(card: Card) {
        progressBar.visibility = VISIBLE
        RetrofitHttp.cardService.addCard(card)
            .enqueue(object : Callback<Card> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<Card>,
                    response: Response<Card>
                ) {
                    progressBar.visibility = GONE
                    cardRepository.saveCard(card)
                    cardAdapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
                }

                override fun onFailure(call: Call<Card>, t: Throwable) {
                    progressBar.visibility = GONE
                    cardRepository.saveCard(card)
                    card.id?.let { cardRepository.changeIsAdded(it, true) }
                    cardAdapter.addCards(ArrayList(cardRepository.getAllSavedCard()))
                }
            })
    }

    private fun isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

    private fun addServer() {
        val cardList = cardRepository.getAllSavedCard()
        if (cardList.isNotEmpty())
            cardList.forEach {
                if (it.isAdded == true) {
                    apiAddCard(it)
                    it.id?.let { it1 -> cardRepository.changeIsAdded(it1, false) }
                }
            }
        else {
            apiCardList()
        }
    }

}