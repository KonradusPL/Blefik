package com.konradpekala.blefik.ui.game

import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.User

interface GameMvp {
    interface View: MvpView{
        fun showUsers(users: List<User>)
        fun refreshCards(cards: List<Card>)
    }
    interface Presenter<V: View>: MvpPresenter<V>{

    }
}