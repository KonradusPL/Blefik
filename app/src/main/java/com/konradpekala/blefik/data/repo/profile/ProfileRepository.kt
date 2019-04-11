package com.konradpekala.blefik.data.repo.profile

import com.konradpekala.blefik.data.preferences.SharedPrefs
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class ProfileRepository(
    val remote: IProfileRepository,
    val cache: SharedPrefs): IProfileRepository {

    override fun saveImageUrl(url: String): Completable {
        return remote.saveImageUrl(url)
    }

    override fun saveImage(imagePath: String): Single<String> {
        return remote.saveImage(imagePath)
            .flatMap{downloadUrl: String -> remote.saveImageUrl(downloadUrl).toSingle { downloadUrl }}
    }

     override fun getProfileImage(): Single<File> {
        return remote.getProfileImage()
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