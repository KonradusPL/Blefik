package com.konradpekala.blefik.ui.profile

import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView

interface ProfileMvp {
    interface View: MvpView{
        fun openLoginActivity()
        fun changeNick(nick: String)
        fun changeEmail(email: String)
        fun changeProfileImage(path: String)
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onChangeNickClick(newNick: String)
        fun onNewImageChosen(path: String)
        fun onLogOutClick()
    }
}