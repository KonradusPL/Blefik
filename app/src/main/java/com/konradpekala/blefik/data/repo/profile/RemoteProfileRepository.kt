package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable

class RemoteProfileRepository(val remoteDb: FirebaseDatabase, val auth: FirebaseAuth): ProfileRepository {
    override fun getNick(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeNick(newNick: String): Completable {
        return remoteDb.changeUserNick(auth.getUserId(),newNick)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}