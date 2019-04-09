package com.konradpekala.blefik.data.preferences

import com.konradpekala.blefik.data.model.User

interface Preferences {
    fun setUserNick(value: String)
    fun setUser(value: User)
    fun getUserNick(): String
    fun isUserLoggedIn(): Boolean
    fun setIsUserLoggedIn(value: Boolean)
    fun setUserEmail(value: String)
    fun getUserEmail(): String
}