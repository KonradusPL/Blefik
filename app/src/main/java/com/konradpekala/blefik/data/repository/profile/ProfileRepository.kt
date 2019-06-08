package com.konradpekala.blefik.data.repository.profile

import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    val remote: IProfileRepository.Remote,
    val cache: Preferences): IProfileRepository {

    fun saveImageUrl(url: String): Completable {
        return remote.saveImageUrl(url)
            .doOnComplete { cache.setProfileImageUrl(url) }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun saveProfile(profile: User, requestType: RequestType): Completable{

        val remoteCompletable = if(requestType == RequestType.FULL) remote.saveProfile(profile)
        else Completable.complete()

        return remoteCompletable.doOnComplete {
            cache.setUser(profile)
            cache.setIsProfileSavedRemotely(true)
        }
    }

    fun setNick(newNick: String): Completable {
        return remote.setNick(newNick)
            .doOnComplete { cache.setUserNick(newNick) }
    }

    fun getEmail(): String {
        return cache.getUserEmail()
    }

    fun getNick(): String {
        return cache.getUserNick()
    }

    fun clean() {
        remote.clean()
    }
}