package com.konradpekala.blefik.ui.createProfile

import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView

interface CreateProfileMvp {
    interface View: MvpView{
        fun showLoading()
        fun hideLoading()
        fun openRoomActivity()
    }
    interface Presenter<V: View>: MvpPresenter<V>{
        fun onAddUserClick(nick: String)
    }
}