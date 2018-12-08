package com.konradpekala.blefik.data.database

import com.konradpekala.blefik.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface Database {
    fun addUser(user: User): Completable
}