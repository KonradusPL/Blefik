package com.konradpekala.blefik.domain.interactors.auth

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.user.GetLocalUserUseCase
import com.konradpekala.blefik.domain.interactors.user.SaveUserUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class SignInUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                        @Named("onObserve") observeScheduler: Scheduler,
                                        private val userSession: UserSession,
                                        private val prefs: Preferences,
                                        private val mSaveUserUseCase: SaveUserUseCase,
                                        private val mGetLocalUserUseCase: GetLocalUserUseCase
): CompletableUseCase<LoginRequest>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: LoginRequest?): Completable {
        return userSession.signIn(request!!.email,request.password)
            .doOnSuccess { prefs.setIsProfileSavedRemotely(true) }
            .flatMap { t: String-> mGetLocalUserUseCase.raw()}
            .flatMapCompletable { user: User -> mSaveUserUseCase.raw(user) }
    }
}