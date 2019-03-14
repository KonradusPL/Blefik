package com.konradpekala.blefik.ui.main

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.ui.base.MvpView

interface MainMvp {
    interface View: MvpView{
        fun openGameActivity(room: Room)
    }
}