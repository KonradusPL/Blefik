package com.konradpekala.blefik.domain.interactors

import android.util.Log
import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class SaveUserUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                          @Named("onObserve") observeScheduler: Scheduler,
                                          private val userRepository: UserRepository,
                                          private val auth: Auth,
                                          private val preferences: Preferences
): CompletableUseCase<User>(subscribeScheduler, observeScheduler) {

    private val TAG ="SaveUserUseCase"

    override fun buildUseCaseCompletable(request: User?): Completable {
        Log.d(TAG,request!!.nick)

        val id = auth.getUserId()
        request.id = id

        val isUserSavedRemotely = preferences.isProfileSavedRemotely()

        val requestType = if (isUserSavedRemotely) RequestType.LOCAL
        else RequestType.FULL

        return userRepository.saveUser(request,requestType)
    }
}