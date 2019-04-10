package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.preferences.SharedPrefs
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class ProfileRepository(
    val remote: RemoteProfileRepository,
    val cache: SharedPrefs) {

     fun saveImage(imagePath: String): Single<String> {
        return remote.saveImage(imagePath)
    }

     fun getProfileImage(): Single<File> {
        return remote.getProfileImage()
    }

     fun changeNick(newNick: String): Completable {
        return remote.changeNick(newNick)
            .doOnComplete { cache.setUserNick(newNick) }
    }

     fun getEmail(): String {
        return cache.getUserEmail()
    }

     fun getNick(): String {
        return cache.getUserNick()
    }
}