package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.profile.FirebaseProfileRepository
import com.konradpekala.blefik.data.repository.profile.ProfileRepository
import com.konradpekala.blefik.data.repository.profile.ProfileRepository_Factory
import com.konradpekala.blefik.domain.usecase.base.CompletableUseCase
import com.konradpekala.blefik.domain.usecase.base.SingleUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class SignInUseCase @Inject constructor(subscribeScheduler: OnSubscribeScheduler,
                    observeScheduler: OnObserveScheduler,
                    private val auth: Auth,
                    private val prefs: Preferences,
                    private val profileRepository: ProfileRepository
): SingleUseCase<LoginRequest, String>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseSingle(request: LoginRequest?): Single<String> {
        return auth.signIn(request!!.email,request.password)
            .flatMap{id: String ->  profileRepository.getNick(id)}
            .doOnSuccess { prefs.setIsProfileSavedRemotely(true) }
    }
}