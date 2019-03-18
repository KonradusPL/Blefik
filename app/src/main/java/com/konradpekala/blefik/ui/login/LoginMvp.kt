package com.konradpekala.blefik.ui.login

import com.konradpekala.blefik.ui.base.MvpPresenter
import com.konradpekala.blefik.ui.base.MvpView

interface LoginMvp {
    interface View: MvpView {
        fun showLoading()
        fun hideLoading()
        fun openMainActivity()
    }
    interface Presenter<V: View>: MvpPresenter<V> {
        fun onSignUpButtonClick(email: String, password: String, nick: String)
        fun onSignInButtonClick(email: String, password: String)
    }
}