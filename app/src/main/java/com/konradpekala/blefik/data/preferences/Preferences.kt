package com.konradpekala.blefik.data.preferences

import com.konradpekala.blefik.data.model.user.User

interface Preferences {
    fun setUserNick(value: String)
    fun getUserNick(): String

    fun setUser(value: User)
    fun getUser(): User

    fun removeUser()

    fun isUserLoggedIn(): Boolean
    fun setIsUserLoggedIn(value: Boolean)

    fun setUserEmail(value: String)
    fun getUserEmail(): String

    fun setProfileImageUrl(value: String)
    fun getProfileImageUrl(): String

    fun setIsProfileSavedRemotely(value: Boolean)
    fun isProfileSavedRemotely(): Boolean
}