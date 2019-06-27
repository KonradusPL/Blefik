package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class GetUsersUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                          @Named("onObserve") observeScheduler: Scheduler,
                                          private val userRepository: UserRepository)
    : SingleUseCase<Unit, List<User>>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<List<User>> {
        return userRepository.getAllUsers()
    }
}