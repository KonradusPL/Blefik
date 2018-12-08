package com.konradpekala.blefik.ui.base

import android.app.Activity
import android.content.Context
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty


open class BaseActivity : AppCompatActivity(), MvpView {

    override fun hideKeyboard() {

    }

    override fun showMessage(message: String) {
        val t = Toasty.info(this,message)
        t.setGravity(Gravity.BOTTOM xor Gravity.CENTER_HORIZONTAL,0,200)
        t.show()
    }

    override fun showMessage(message: Int) {
        ///Toasty.info(this,TextUtils.t).show()
    }

    override fun isConnectedToNetwork(): Boolean {
        return true
    }

    override fun getCtx(): Context {
        return this
    }

    override fun getActivity(): Activity {
        return this
    }
}