package com.konradpekala.blefik.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single

interface UserSession {

    fun isUserLoggedIn():Boolean

    fun signUp(email: String, password: String): Completable

    fun signIn(email: String, password: String): Single<String>

    fun logOut()

    fun getUserId(): String
}