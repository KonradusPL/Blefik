package com.konradpekala.blefik.ui.game

import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.game.adapters.PlayersAdapter

interface GameMvp {
    interface View: MvpView{
        fun getPresenter(): GamePresenter<View>
        fun getBidAdapter(): CardsAdapter
        fun getPlayerCardsAdapter(): CardsAdapter
        fun getPlayersAdapter(): PlayersAdapter
        fun animateBidChanges()
        fun openBidCreator()
        fun closeBidCreator()
        fun openMainActivity()
    }

    interface Presenter<V: View>: MvpPresenter<V>{
        fun startGame(roomId: String, creatorId: String,firstT: Boolean)
        fun onCreateBidClick(bid: Bid)
        fun onRaiseBidClick()
        fun onCheckBidClick()
        fun refreshGame()
    }
}