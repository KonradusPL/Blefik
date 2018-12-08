package com.konradpekala.blefik.data.repo

import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.SharedPreferences
import io.reactivex.Completable

class CreateProfileRepo(private val db: Database, private val prefs: SharedPreferences) {

    fun addUser(user: User): Completable{
        return Completable.create { emitter ->
            prefs.saveCurrentUser(user.fullName)
            emitter.onComplete()
        }
    }
}