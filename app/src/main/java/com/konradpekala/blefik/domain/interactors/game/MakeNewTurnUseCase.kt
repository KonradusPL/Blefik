package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.game.errors.BidNotHigher
import com.konradpekala.blefik.domain.interactors.game.errors.BidNotProperlyCreated
import com.konradpekala.blefik.utils.BidUtils
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MakeNewTurnUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                             @Named("onObserve") observeScheduler: Scheduler,
                                             private val mGameSession: GameSession,
                                             private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Unit>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: Unit?): Completable {
        val room = mGameSession.getCurrentRoom().copy()
        room.updateType = UpdateType.NextPlayer
        calculateNextPlayer(room)

        return mRoomsRepository.updateRoom(room)
    }

    private fun calculateNextPlayer(room: Room){
        if(room.currentPlayer < room.players.size-1)
            room.currentPlayer++
        else
            room.currentPlayer = 0
    }

}