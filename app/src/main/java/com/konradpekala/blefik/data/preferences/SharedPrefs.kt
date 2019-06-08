package com.konradpekala.blefik.data.preferences

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.konradpekala.blefik.data.model.User
import javax.inject.Inject

class SharedPrefs @Inject constructor(context: Context): Preferences {

    private val USER_NICK = "USER_NICK"
    private val USER_EMAIL = "USER_EMAIL"
    private val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
    private val URL_PROFILE_IMAGE = "URL_PROFILE_IMAGE"
    private val IS_PROFILE_SAVED = "IS_PROFILE_CREATED"


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
        clean()
        setIsUserLoggedIn(true)
        setUserEmail(value.email)
        setUserNick(value.nick)
    }

    private fun clean(){
        setIsProfileSavedRemotely(false)
        setIsUserLoggedIn(false)
    }

    override fun setUserEmail(value: String) {
        mSharedPrefs.edit {
            putString(USER_EMAIL,value)
        }
    }

    override fun getUserEmail(): String {
        return mSharedPrefs.getString(USER_EMAIL,"") ?: ""
    }

    override fun isUserLoggedIn(): Boolean {
        return mSharedPrefs.getBoolean(IS_USER_LOGGED_IN,false)
    }

    override fun getProfileImageUrl(): String {
        return mSharedPrefs.getString(URL_PROFILE_IMAGE,"") ?: ""
    }

    override fun setProfileImageUrl(value: String) {
        Log.d("setProfileImageUrl",value)
        mSharedPrefs.edit {
            putString(URL_PROFILE_IMAGE,value)
        }
    }

    override fun setIsProfileSavedRemotely(value: Boolean) {
        mSharedPrefs.edit {
            putBoolean(IS_PROFILE_SAVED,value)
        }
    }

    override fun isProfileSavedRemotely(): Boolean {
        return mSharedPrefs.getBoolean(IS_PROFILE_SAVED,false)
    }


}