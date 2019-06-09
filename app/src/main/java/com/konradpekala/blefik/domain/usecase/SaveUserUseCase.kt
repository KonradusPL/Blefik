package com.konradpekala.blefik.domain.usecase

import android.util.Log
import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.domain.usecase.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(subscribeScheduler: OnSubscribeScheduler,
                                          observeScheduler: OnObserveScheduler,
                                          private val userRepository: UserRepository,
                                          private val auth: Auth,
                                          private val preferences: Preferences
): CompletableUseCase<User>(subscribeScheduler, observeScheduler) {

    private val TAG ="SaveUserUseCase"

    override fun buildUseCaseCompletable(request: User?): Completable {
        val id = auth.getUserId()
        request!!.id = id

        Log.d(TAG,request.nick)

        val isUserSavedRemotely = preferences.isProfileSavedRemotely()

        val requestType = if (isUserSavedRemotely) RequestType.LOCAL
        else RequestType.FULL

        return userRepository.saveUser(request,requestType)
    }
}