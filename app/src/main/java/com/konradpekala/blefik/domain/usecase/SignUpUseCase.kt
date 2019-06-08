package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.domain.usecase.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class SignUpUseCase @Inject constructor(subscribeScheduler: OnSubscribeScheduler,
                    observeScheduler: OnObserveScheduler,
                    private val auth: Auth,
                    private val preferences: Preferences
): CompletableUseCase<LoginRequest>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: LoginRequest?): Completable {
        return auth.signUp(request!!.email,request.password)
            .toCompletable()
            .doOnComplete {  preferences.setIsProfileSavedRemotely(false)}
    }
}