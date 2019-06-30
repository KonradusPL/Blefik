package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.image.UrlType
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class UpdateImageUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                             @Named("onObserve") observeScheduler: Scheduler,
                                             private val mImageRepository: ImageRepository,
                                             private val mUserRepository: UserRepository,
                                             private val mUserSession: UserSession
): SingleUseCase<String,String>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseSingle(request: String?): Single<String> {
        val imagePath = request!!

        return mImageRepository.uploadImageWithUserIdAsName(imagePath, mUserSession.getUserId())
            .andThen(Single.defer {  mImageRepository.getImageUrl(UrlType.REMOTE) })
            .flatMapCompletable { remoteImageUrl: String ->  mUserRepository.saveImageUrl(remoteImageUrl)}
            .andThen(Single.defer {  mImageRepository.getImageUrl(UrlType.REMOTE) })
    }

}