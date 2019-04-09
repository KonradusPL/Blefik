package com.konradpekala.blefik.data.repo.profile

import android.graphics.Bitmap
import com.konradpekala.blefik.data.preferences.SharedPrefs
import io.reactivex.Completable
import io.reactivex.Single

class ProfileRepositoryImpl(val remote: RemoteProfileRepository,
                            val local: LocalProfileRepository,
                            val cache: SharedPrefs): ProfileRepository {

    override fun saveImage(imagePath: String): Single<String> {
        return remote.saveImage(imagePath)
    }

    override fun getImage(url: String): Single<Bitmap> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeNick(newNick: String): Completable {
        return remote.changeNick(newNick)
            .doOnComplete { cache.setUserNick(newNick) }
    }

    override fun getEmail(): String {
        return cache.getUserEmail()
    }

    override fun getNick(): String {
        return cache.getUserNick()
    }
}