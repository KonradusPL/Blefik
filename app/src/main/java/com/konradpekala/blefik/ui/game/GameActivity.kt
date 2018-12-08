package com.konradpekala.blefik.ui.game

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.repo.GameRepository
import com.konradpekala.blefik.ui.game.adapters.CurrentCardsAdapter
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity(),GameMvp.View {

    private val mPresenter = GamePresenter<GameMvp.View>(this, GameRepository())
    private lateinit var mCardsAdapter: CurrentCardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initCardsList()

        mPresenter.start()
    }

    private fun initCardsList(){
        mCardsAdapter = CurrentCardsAdapter(ArrayList(), this)
        listCurrentCards.adapter = mCardsAdapter
        listCurrentCards.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
    }

    override fun showUsers(users: List<User>) {
    }

    override fun refreshCards(cards: List<Card>) {
        mCardsAdapter.refreshCards(cards)
    }
}
