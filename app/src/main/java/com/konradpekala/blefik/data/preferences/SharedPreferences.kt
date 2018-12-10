package com.konradpekala.blefik.data.preferences

import android.content.Context
import androidx.core.content.edit

class SharedPreferences(context: Context): Preferences {

    private val CURRENT_USER_FULLNAME = "CURRENT_USER_FULLNAME"
    private val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"


    private val mSharedPrefs = context.getSharedPreferences("shared_preferences",Context.MODE_PRIVATE)

    override fun saveCurrentUser(value: String) {
        mSharedPrefs.edit {
            putBoolean(IS_USER_LOGGED_IN,true)
            putString(CURRENT_USER_FULLNAME,value)
        }
    }

    override fun getUserName(): String {
        return mSharedPrefs.getString(CURRENT_USER_FULLNAME,"") ?: ""
    }

    override fun isUserLoggedIn(): Boolean {
        return mSharedPrefs.getBoolean(IS_USER_LOGGED_IN,false)
    }
}