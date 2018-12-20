package com.konradpekala.blefik.ui.game

import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.ui.game.adapters.CardsAdapter
import com.konradpekala.blefik.ui.room.RoomsMvp

interface GameMvp {
    interface View: MvpView{
        fun showUsers(users: List<User>)
        fun getPresenter(): RoomsMvp.Presenter<RoomsMvp.View>
        fun getBidAdapter(): CardsAdapter
        fun getPlayerCardsAdapter(): CardsAdapter
    }

    interface Presenter<V: View>: MvpPresenter<V>{
        fun startGame(roomId: String, isCreator: Boolean)
    }
}