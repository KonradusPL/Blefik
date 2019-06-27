package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.error_models.EmptyRoom
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class ChangeRoomToStartUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                                   @Named("onObserve") observeScheduler: Scheduler,
                                                   private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Room>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: Room?): Completable {
        if(request!!.players.size < 2)
            return Completable.error(EmptyRoom())

        return mRoomsRepository.changeRoomToStarted(request)
    }
}