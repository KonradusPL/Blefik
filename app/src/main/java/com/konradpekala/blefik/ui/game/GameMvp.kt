package com.konradpekala.blefik.ui.game

import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.game.adapters.PlayersAdapter
import com.konradpekala.blefik.ui.room.RoomsMvp

interface GameMvp {
    interface View: MvpView{
        fun getPresenter(): GameMvp.Presenter<GameMvp.View>
        fun getBidAdapter(): CardsAdapter
        fun getPlayerCardsAdapter(): CardsAdapter
        fun getPlayersAdapter(): PlayersAdapter
        fun animateBidChanges()
        fun openBidCreator()
        fun closeBidCreator()
        fun openRoomActivity()
    }

    interface Presenter<V: View>: MvpPresenter<V>{
        fun startGame(roomId: String, creatorId: String,firstT: Boolean)
        fun onCreateBidClick(bid: Bid)
        fun onRaiseBidClick()
        fun onCheckBidClick()
        fun refreshGame()
    }
}