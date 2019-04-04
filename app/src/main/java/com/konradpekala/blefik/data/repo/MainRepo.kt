package com.konradpekala.blefik.data.repo

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable

class MainRepo(val db: FirebaseDatabase,val auth: FirebaseAuth,val prefs: SharedPrefs) {

    fun getUserNick(): String = prefs.getUserNick()

    fun logOut(){
        auth.logOut()
    }

    fun changeNick(newNick: String): Completable{
        return db.changeUserNick(auth.getUserId(),newNick)
            .doOnComplete {prefs.setUserNick(newNick)}
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}