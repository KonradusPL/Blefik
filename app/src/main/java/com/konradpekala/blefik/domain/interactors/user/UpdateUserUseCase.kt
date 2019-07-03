package com.konradpekala.blefik.domain.interactors.user

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.model.UserUpdateRequest
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class UpdateUserUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                           @Named("onObserve") observeScheduler: Scheduler,
                                           private val mUserRepository: UserRepository,
                                           private val mUserSession: UserSession
): CompletableUseCase<UserUpdateRequest>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: UserUpdateRequest?): Completable {
        val value = request!!.value.toString()

        val userId = mUserSession.getUserId()

        return when(request.valueToUpdate){
            ValueToUpdate.NICK -> mUserRepository.setNick(request.value.toString())
            ValueToUpdate.EMAIL -> mUserSession.updateEmail(value)
                .andThen(Completable.defer { mUserRepository.setEmail(request.value.toString()) })
            ValueToUpdate.GAMES_WON -> mUserRepository.updateGamesWon(userId,request.value as Int)
        }
    }

}