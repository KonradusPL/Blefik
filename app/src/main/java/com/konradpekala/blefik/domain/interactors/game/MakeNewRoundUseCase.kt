package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.CardsUtils
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MakeNewRoundUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                              @Named("onObserve") observeScheduler: Scheduler,
                                              private val mGameSession: GameSession,
                                              private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Boolean>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: Boolean?): Completable {
        val room = getRoomWithNewRound(request!!)
        return mRoomsRepository.updateRoom(room)
    }

    private fun getRoomWithNewRound(isRoundFirst: Boolean): Room {
        val currentRoomCopy = mGameSession.getCurrentRoom().copy()
        currentRoomCopy.apply {
            updateType = UpdateType.NewGame
            currentBid = null
            CardsUtils.cardsForNewRound(players,isRoundFirst)

            if(isRoundFirst)
                currentPlayer = 0
            else{
                if(currentPlayer < players.size-1)
                    currentPlayer++
                else
                    currentPlayer = 0
            }
        }
        return currentRoomCopy
    }

}