package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class SignInUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                        @Named("onObserve") observeScheduler: Scheduler,
                                        private val auth: Auth,
                                        private val prefs: Preferences,
                                        private val userRepository: UserRepository
): SingleUseCase<LoginRequest, String>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseSingle(request: LoginRequest?): Single<String> {
        return auth.signIn(request!!.email,request.password)
            .flatMap{id: String ->  userRepository.getNick(id)}
            .doOnSuccess { prefs.setIsProfileSavedRemotely(true) }
    }
}