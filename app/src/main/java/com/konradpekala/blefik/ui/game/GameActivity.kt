package com.konradpekala.blefik.ui.game

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.repo.GameRepository
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.room.RoomsMvp
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity(),GameMvp.View {

    private val mPresenter = GamePresenter<GameMvp.View>(this, GameRepository())
    private lateinit var mBidAdapter: CardsAdapter
    private lateinit var mPlayerCardsAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if(intent != null){
            toolbarGame.title = intent.getStringExtra("roomName") ?: ""
        }

        initBidList()
        initPlayerCardsList()

        mPresenter.start()
    }

    private fun initBidList(){
        mBidAdapter = CardsAdapter(ArrayList(), this)
        listCurrentBid.adapter = mBidAdapter
        listCurrentBid.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
    }

    private fun initPlayerCardsList(){
        mPlayerCardsAdapter = CardsAdapter(ArrayList(), this)
        listCurrentCards.adapter = mPlayerCardsAdapter
        listCurrentCards.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
    }

    override fun getPresenter(): RoomsMvp.Presenter<RoomsMvp.View> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBidAdapter(): CardsAdapter {
        return mBidAdapter
    }

    override fun getPlayerCardsAdapter(): CardsAdapter {
        return mPlayerCardsAdapter
    }


    override fun showUsers(users: List<User>) {
    }

}
