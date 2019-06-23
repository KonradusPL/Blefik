package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class SignInUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                        @Named("onObserve") observeScheduler: Scheduler,
                                        private val auth: Auth,
                                        private val prefs: Preferences,
                                        private val mSaveUserUseCase: SaveUserUseCase,
                                        private val mGetLocalUserUseCase: GetLocalUserUseCase
): CompletableUseCase<LoginRequest>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: LoginRequest?): Completable {
        return auth.signIn(request!!.email,request.password)
            .doOnSuccess { prefs.setIsProfileSavedRemotely(true) }
            .flatMap { t: String-> mGetLocalUserUseCase.raw()}
            .flatMapCompletable { user: User -> mSaveUserUseCase.raw(user) }
    }
}