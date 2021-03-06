package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class GetProfileImageUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                                 @Named("onObserve") observeScheduler: Scheduler,
                                                 private val userSession: UserSession,
                                                 private val imageRepository: ImageRepository)
    : SingleUseCase<Unit, File>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<File> {
        return imageRepository.getImageFile(userSession.getUserId())
    }
}