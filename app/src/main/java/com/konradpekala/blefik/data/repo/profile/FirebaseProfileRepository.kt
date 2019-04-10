package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class FirebaseProfileRepository(val remoteDb: FirebaseDatabase,
                                val auth: FirebaseAuth,
                                val storage: FirebaseStorage): RemoteProfileRepository {

    override fun getNick(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage(imagePath: String): Single<String> {
        return storage.saveImage(imagePath,auth.getUserId())
    }

    override fun getProfileImage(): Single<File> {
        return storage.getProfileImage(auth.getUserId())
    }

    override fun changeNick(newNick: String): Completable {
        return remoteDb.changeUserNick(auth.getUserId(),newNick)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}