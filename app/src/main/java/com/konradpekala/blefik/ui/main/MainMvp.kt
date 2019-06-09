package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView
import java.io.File

interface MainMvp {
    interface View: MvpView{
        fun openGameActivity(room: Room)
        fun openLoginActivity()
        fun changeProfileImage(file: File)
        fun openProfileActivity()
        fun setToolbarTitle(title: String)
    }
    interface Presenter{

    }
}