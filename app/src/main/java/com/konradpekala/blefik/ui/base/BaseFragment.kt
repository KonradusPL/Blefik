package com.konradpekala.blefik.ui.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment


open class BaseFragment<V: MvpView>: DaggerFragment(),MvpView {

    lateinit var parentMvp: V
    lateinit var parentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentMvp = context as V
        parentContext = context
    }

    override fun showMessage(message: String) {
        parentMvp.showMessage(message)
    }

    override fun showMessage(message: Int) {
        parentMvp.showMessage(message)
    }

    override fun isConnectedToNetwork(): Boolean {
        return parentMvp.isConnectedToNetwork()
    }


    override fun hideKeyboard() {
        parentMvp.hideKeyboard()
    }


    override fun getCtx(): Context {
        return parentMvp.getCtx()
    }

}