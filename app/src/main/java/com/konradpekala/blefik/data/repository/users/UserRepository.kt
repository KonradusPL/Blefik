package com.konradpekala.blefik.data.repository.users

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val mRemote: IUserRepository.Remote,
    private val mCache: Preferences,
    private val mAuth: Auth) {

    private var mLocalPlayer: Player? = null

    fun saveImageUrl(url: String): Completable {
        return mRemote.saveImageUrl(url)
            .doOnComplete { mCache.setProfileImageUrl(url) }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun saveUser(user: User, requestType: RequestType): Completable{
        val remoteCompletable = if(requestType == RequestType.FULL) mRemote.saveUser(user)
        else Completable.complete()

        return remoteCompletable.doOnComplete {
            mCache.setUser(user)
            mCache.setIsProfileSavedRemotely(true)
        }
    }

    fun getUser(id: String): Single<User>{
        if(isUserIdLocal(id)){
            val cachedUser = mCache.getUser()
            if (cachedUser.email.isNotEmpty()){
                cachedUser.id = id
                return Single.just(cachedUser)
            }
        }
        return mRemote.getUser(id)
    }

    private fun isUserIdLocal(id: String): Boolean {
        return id == mAuth.getUserId()
    }

    fun setNick(newNick: String): Completable {
        return mRemote.setNick(newNick)
            .doOnComplete { mCache.setUserNick(newNick) }
    }

    fun getEmail(): String {
        return mCache.getUserEmail()
    }

    fun getNick(id: String): Single<String> {
        if (mCache.getUserNick() != "")
            return Single.just(mCache.getUserNick())
        else{
            return mRemote.getNick(id)
        }
    }

    fun getNickLocally() = mCache.getUserNick()

    fun clean() {
        mRemote.clean()
    }

    fun getAllUsers(): Single<List<User>>{
        return mRemote.getAllUsers()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun getLocalPlayer(): Player{
        if(mLocalPlayer == null){
            val creator = mCache.getUserNick()
            val id = mAuth.getUserId()
            val imageUrl = mCache.getProfileImageUrl()
            mLocalPlayer = Player(id,creator,imageUrl)
        }
        return mLocalPlayer!!
    }
}