package com.konradpekala.blefik.ui.base

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import androidx.core.content.ContextCompat.getSystemService




open class BaseActivity : AppCompatActivity(), MvpView {

    override fun hideKeyboard() {
        //all lines from StackOverflow
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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

}