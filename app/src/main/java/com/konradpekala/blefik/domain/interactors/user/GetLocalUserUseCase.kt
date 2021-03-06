package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.model.user.UserBuilder
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import java.io.File
import javax.inject.Inject
import javax.inject.Named

class GetLocalUserUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                              @Named("onObserve") observeScheduler: Scheduler,
                                              private val mUserRepository: UserRepository,
                                              private val mImageRepository: ImageRepository,
                                              private val mUserSession: UserSession
                      )
    : SingleUseCase<Unit, User>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<User> {
        return mUserRepository.getUser(mUserSession.getUserId())
    }

    /*At this time GetLocalUserUseCase doesn't download image file since
    //downloading time is very long*/
    private fun joinImageFileWithUser( user: User): Single<User>{
      return mImageRepository.getImageFile(user.id)
          .map { file: File ->  UserBuilder.withUser(user).withImageFile(file).build()}
    }
}