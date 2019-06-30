package com.konradpekala.blefik.data.repository.users

import android.util.Log
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.utils.SchedulerProvider
import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val mRemote: IUserRepository.Remote,
    private val mCache: Preferences,
    private val mUserSession: UserSession) {

    private val TAG = "UserRepository"

    private var mLocalPlayer: Player? = null

    fun saveImageUrl(url: String): Completable {
        return mRemote.saveImageUrl(url)
            .doOnComplete { mCache.setProfileImageUrl(url) }
    }

    fun saveUser(user: User, requestType: RequestType): Completable{
        user.id = mUserSession.getUserId()

        val remoteCompletable = if(requestType == RequestType.FULL) mRemote.saveUser(user)
        else Completable.complete()

        return remoteCompletable.doOnComplete {
            Log.d(TAG,"remoteCompletable.doOnComplete")
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
        return id == mUserSession.getUserId()
    }

    fun setNick(newNick: String): Completable {
        return mRemote.updateValue(newNick,ValueToUpdate.NICK)
            .doOnComplete { mCache.setUserNick(newNick) }
    }
    fun setEmail(newEmail: String): Completable {
        return mRemote.updateValue(newEmail,ValueToUpdate.EMAIL)
            .doOnComplete { mCache.setUserEmail(newEmail) }
    }

    fun clean() {
        mRemote.clean()
        mLocalPlayer = null
        mCache.removeUser()
    }

    fun getAllUsers(): Single<List<User>>{
        return mRemote.getAllUsers()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun getLocalPlayer(): Player{
        if(mLocalPlayer == null){
            val creator = mCache.getUserNick()
            val id = mUserSession.getUserId()
            val imageUrl = mCache.getProfileImageUrl()
            mLocalPlayer = Player(id,creator,imageUrl)
        }
        return mLocalPlayer!!
    }
}