package com.konradpekala.blefik.data.repository.users

import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class  UserRepository @Inject constructor(
    val remote: IUserRepository.Remote,
    val cache: Preferences) {

    fun saveImageUrl(url: String): Completable {
        return remote.saveImageUrl(url)
            .doOnComplete { cache.setProfileImageUrl(url) }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun saveUser(user: User, requestType: RequestType): Completable{

        val remoteCompletable = if(requestType == RequestType.FULL) remote.saveUser(user)
        else Completable.complete()

        return remoteCompletable.doOnComplete {
            cache.setUser(user)
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

    fun getNick(id: String): Single<String> {
        if (cache.getUserNick() != "")
            return Single.just(cache.getUserNick())
        else{
            return remote.getNick(id)
        }
    }

    fun clean() {
        remote.clean()
    }

    fun getAllUsers(): Single<List<User>>{
        return remote.getAllUsers()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}