package com.konradpekala.blefik.domain.usecase

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.usecase.base.SingleUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    subscribeScheduler: OnSubscribeScheduler,
    observeScheduler: OnObserveScheduler,
    private val userRepository: UserRepository
)
    : SingleUseCase<Unit, List<User>>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<List<User>> {
        return userRepository.getAllUsers()
    }
}