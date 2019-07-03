package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class RemovePlayerUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                              @Named("onObserve") observeScheduler: Scheduler,
                                              private val mGameSession: GameSession,
                                              private val mRoomsRepository: RoomsRepository
)
    : CompletableUseCase<Player>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseCompletable(request: Player?): Completable {
        val playerToRemove = request!!
        mGameSession.removeBeatenFromCache(playerToRemove)

        val room = mGameSession.getCurrentRoom()
        room.updateType = UpdateType.PlayerBeaten
        return mRoomsRepository.updateRoom(room)
    }

}