package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import io.reactivex.Completable
import io.reactivex.Single

class LocalProfileRepository: ProfileRepository {
    override fun getNick(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage(imagePath: String): Single<String> {
        TODO()
    }

    override fun getImage(url: String): Single<Bitmap> {
        TODO()
    }

    override fun changeNick(newNick: String): Completable {
        TODO("cache is doing work")
    }
}