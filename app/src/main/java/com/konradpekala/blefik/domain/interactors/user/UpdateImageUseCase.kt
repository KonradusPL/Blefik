package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class UpdateImageUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                             @Named("onObserve") observeScheduler: Scheduler,
                                             private val mImageRepository: ImageRepository,
                                             private val mUserRepository: UserRepository,
                                             private val mUserSession: UserSession
): CompletableUseCase<String>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: String?): Completable {
        val imagePath = request!!

        return mImageRepository.uploadImageWithUserIdAsName(imagePath, mUserSession.getUserId())
            .andThen(mUserRepository.saveImageUrl(imagePath))
    }

}