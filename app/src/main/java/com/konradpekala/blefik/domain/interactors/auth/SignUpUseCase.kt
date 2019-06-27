package com.konradpekala.blefik.domain.interactors.auth

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.request.RegisterRequest
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.user.SaveUserUseCase
import io.reactivex.*
import javax.inject.Inject
import javax.inject.Named

class SignUpUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                        @Named("onObserve") observeScheduler: Scheduler,
                                        private val mUserSession: UserSession,
                                        private val mPreferences: Preferences,
                                        private val mSaveUserUseCase: SaveUserUseCase
): CompletableUseCase<RegisterRequest>(subscribeScheduler, observeScheduler) {

    private val TAG = "SignUpUseCase"

    override fun buildUseCaseCompletable(request: RegisterRequest?): Completable {
        val user = User()
        mapRequestToUser(request!!,user)

        return mUserSession.signUp(request.email,request.password)
            .doOnComplete { mPreferences.setIsProfileSavedRemotely(false) }
            .andThen( Completable.defer { mSaveUserUseCase.raw(user) } )

    }

    private fun mapRequestToUser(request: RegisterRequest, user: User){
        user.nick = request.nick
        user.email = request.email
        user.password = request.password
    }
}