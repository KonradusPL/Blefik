package com.konradpekala.blefik.data.repository

import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.SharedPrefs
import io.reactivex.Completable

class CreateProfileRepo(private val db: Database, private val prefs: SharedPrefs) {

    fun addUser(user: User): Completable{
        return Completable.create { emitter ->
            prefs.setUserNick(user.nick)
            emitter.onComplete()
        }
    }
}