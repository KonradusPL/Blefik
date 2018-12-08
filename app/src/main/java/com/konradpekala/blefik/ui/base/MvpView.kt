package com.konradpekala.blefik.ui.base

import android.app.Activity
import android.content.Context

interface MvpView {

    fun hideKeyboard()

    fun showMessage(message: String)
    fun showMessage(message: Int)

    fun isConnectedToNetwork(): Boolean

    fun getCtx(): Context
    fun getActivity(): Activity
}