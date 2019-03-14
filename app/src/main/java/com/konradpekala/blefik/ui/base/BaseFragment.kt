package com.konradpekala.blefik.ui.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment<V: MvpView>: Fragment(),MvpView {

    lateinit var parentMvp: V
    lateinit var parentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentMvp = context as V
        parentContext = context
    }

    override fun showMessage(message: String) {

    }

    override fun showMessage(message: Int) {
    }

    override fun isConnectedToNetwork(): Boolean {
        return parentMvp.isConnectedToNetwork()
    }


    override fun hideKeyboard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getCtx(): Context {
        return parentMvp.getCtx()
    }

}