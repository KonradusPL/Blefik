package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class ProfileRepository(
    val remote: IProfileRepository,
    val cache: SharedPrefs): IProfileRepository {

    override fun saveImageUrl(url: String): Completable {
        return remote.saveImageUrl(url)
            .doOnComplete { cache.setProfileImageUrl(url) }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

     override fun setNick(newNick: String): Completable {
        return remote.setNick(newNick)
            .doOnComplete { cache.setUserNick(newNick) }
    }

     override fun getEmail(): String {
        return cache.getUserEmail()
    }

     override fun getNick(): String {
        return cache.getUserNick()
    }

    override fun clean() {
        remote.clean()
    }
}