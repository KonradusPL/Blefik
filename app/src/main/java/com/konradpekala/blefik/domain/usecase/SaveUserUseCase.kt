package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.profile.ProfileRepository
import com.konradpekala.blefik.data.repository.utils.RequestType
import com.konradpekala.blefik.domain.usecase.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(subscribeScheduler: OnSubscribeScheduler,
                      observeScheduler: OnObserveScheduler,
                      private val profileRepository: ProfileRepository,
                      private val auth: Auth,
                      private val preferences: Preferences
): CompletableUseCase<User>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: User?): Completable {
        val id = auth.getUserId()
        request!!.id = id

        val isUserSaved = preferences.isProfileSavedRemotely()

        val requestType = if (isUserSaved) RequestType.LOCAL
        else RequestType.LOCAL

        return profileRepository.saveProfile(request,requestType)
    }
}