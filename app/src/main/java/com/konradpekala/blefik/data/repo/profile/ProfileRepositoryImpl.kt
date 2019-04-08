package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.preferences.SharedPrefs
import io.reactivex.Completable

class ProfileRepositoryImpl(val remote: RemoteProfileRepository,
                            val local: LocalProfileRepository,
                            val cache: SharedPrefs): ProfileRepository {
    override fun saveImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getImage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeNick(newNick: String): Completable {
        return remote.changeNick(newNick)
            .doOnComplete { cache.setUserNick(newNick) }
    }
}