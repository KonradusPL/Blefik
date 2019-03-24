package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun openGameActivity(room: Room)
        fun openLoginActivity()
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onChangeNickClick(newNick: String)
        fun onLogOutClick()
    }
}