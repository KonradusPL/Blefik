package com.konradpekala.blefik.ui.game

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konradpekala.blefik.R
import com.konradpekala.blefik.ui.base.BaseActivity
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.injection.Injector
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.game.adapters.PlayersAdapter
import com.konradpekala.blefik.ui.room.RoomsMvp
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.ctx

class GameActivity : BaseActivity(),GameMvp.View {

    private lateinit var mPresenter: GamePresenter<GameMvp.View>

    private lateinit var mBidAdapter: CardsAdapter
    private lateinit var mPlayerCardsAdapter: CardsAdapter
    private lateinit var mPlayersAdapter: PlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        mPresenter = Injector.getGamePresenter(this,ctx)

        toolbarGame.title = intent.getStringExtra("roomName") ?: ""


        initBidList()
        initPlayerCardsList()
        initPlayersList()

        val roomId = intent.getStringExtra("roomId")
        val creatorId = intent.getStringExtra("creatorId")
        //**
        //****
        //******
        mPresenter.startGame(roomId,creatorId)
        //******
        //****
        //**
    }

    private fun initPlayersList(){
        mPlayersAdapter = PlayersAdapter(ArrayList(),this)
        listUsers.adapter = mPlayersAdapter
        listUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
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

    override fun getPlayersAdapter(): PlayersAdapter {
        return mPlayersAdapter
    }

    override fun getPlayerCardsAdapter(): CardsAdapter {
        return mPlayerCardsAdapter
    }


    override fun showUsers(users: List<User>) {
    }

}
