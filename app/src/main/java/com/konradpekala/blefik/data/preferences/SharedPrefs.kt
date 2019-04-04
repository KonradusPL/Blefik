package com.konradpekala.blefik.data.preferences

import android.content.Context
import androidx.core.content.edit
import com.konradpekala.blefik.data.model.User

class SharedPrefs(context: Context): Preferences {

    private val USER_NICK = "USER_NICK"
    private val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"


    private val mSharedPrefs = context.getSharedPreferences("shared_preferences",Context.MODE_PRIVATE)

    override fun setUserNick(value: String) {
        mSharedPrefs.edit {
            putString(USER_NICK,value)
        }
    }

    override fun setIsUserLoggedIn(value: Boolean) {
        mSharedPrefs.edit{
            putBoolean(IS_USER_LOGGED_IN,value)
        }
    }

    override fun getUserNick(): String {
        return mSharedPrefs.getString(USER_NICK,"") ?: ""
    }

    override fun setUser(value: User) {
        TODO()
    }

    override fun isUserLoggedIn(): Boolean {
        return mSharedPrefs.getBoolean(IS_USER_LOGGED_IN,false)
    }
}