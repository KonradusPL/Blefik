package com.konradpekala.blefik.ui.profile

import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import java.io.File

interface ProfileMvp {
    interface View: MvpView{
        fun openLoginActivity()
        fun changeNick(nick: String)
        fun changeEmail(email: String)
        fun changeProfileImage(file: File)
        fun changeProfileImage(uri: String)
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onChangeNickClick(newNick: String)
        fun onNewImageChosen(imagePath: String)
        fun onLogOutClick()
    }
}