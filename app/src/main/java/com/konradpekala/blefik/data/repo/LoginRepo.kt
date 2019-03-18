package com.konradpekala.blefik.data.repo

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

class LoginRepository(private val mAuth: FirebaseAuth, private val mDb: FirebaseDatabase) {

    fun signUp(email: String, password: String): Single<String> {
        return mAuth.signUp(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun signIn(email: String, password: String): Completable {
        return mAuth.signIn(email,password)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun createUser(email: String,password: String,id: String,nick: String): Single<String>{
        return mDb.createUser(email, password, id,nick)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}