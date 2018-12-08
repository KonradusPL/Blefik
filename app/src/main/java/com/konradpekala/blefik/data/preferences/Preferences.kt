package com.konradpekala.blefik.data.preferences

interface Preferences {
    fun saveCurrentUser(value: String)
    fun getUserName(): String
}