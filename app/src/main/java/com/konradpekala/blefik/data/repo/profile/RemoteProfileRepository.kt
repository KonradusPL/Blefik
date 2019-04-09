package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.FirebaseDatabase
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

class RemoteProfileRepository(val remoteDb: FirebaseDatabase,
                              val auth: FirebaseAuth,
                                val storage: FirebaseStorage): ProfileRepository {

    override fun getNick(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage(imageBitmap: Bitmap): Single<String> {
        return storage.saveImage(imageBitmap,auth.getUserId())
    }

    override fun getImage(url: String): Single<Bitmap> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeNick(newNick: String): Completable {
        return remoteDb.changeUserNick(auth.getUserId(),newNick)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}