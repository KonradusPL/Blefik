package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class SignUpUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                        @Named("onObserve") observeScheduler: Scheduler,
                                        private val auth: Auth,
                                        private val preferences: Preferences
): CompletableUseCase<LoginRequest>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: LoginRequest?): Completable {
        return auth.signUp(request!!.email,request.password)
            .doOnComplete {  preferences.setIsProfileSavedRemotely(false)}
    }
}