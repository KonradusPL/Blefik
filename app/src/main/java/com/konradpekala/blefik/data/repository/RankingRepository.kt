package com.konradpekala.blefik.data.repository

import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Single

class RankingRepository(val db: FirebaseDatabase) {
    fun getUsers(): Single<List<User>>{
        return db.getUsers()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}