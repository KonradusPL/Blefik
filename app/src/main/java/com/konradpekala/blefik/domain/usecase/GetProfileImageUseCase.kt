package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.domain.usecase.base.SingleUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class GetProfileImageUseCase @Inject constructor(
    subscribeScheduler: OnSubscribeScheduler,
    observeScheduler: OnObserveScheduler,
    private val auth: Auth,
    private val imageRepository: ImageRepository)
    : SingleUseCase<Unit,File>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<File> {
        return imageRepository.getProfileImage(auth.getUserId())
    }
}