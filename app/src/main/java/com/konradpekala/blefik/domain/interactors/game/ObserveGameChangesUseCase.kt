package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class ObserveGameChangesUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                                    @Named("onObserve") observeScheduler: Scheduler,
                                                    private val mRoomsRepository: RoomsRepository,
                                                    private val mGameSession: GameSession,
                                                    private val mUserSession: UserSession
) : ObservableUseCase<Unit, Room>(subscribeScheduler,observeScheduler) {



    override fun buildUseCaseObservable(params: Unit?): Observable<Room> {
        val roomId = getRoomId()
        return mRoomsRepository.observeRoom(roomId)
            .filter { room: Room ->  mGameSession.hasRoom()}
            .map { room: Room -> getModifiedRoom(room) }
            .doOnNext { room: Room -> mGameSession.updateStartedRoom(room) }

    }

    private fun getRoomId() =  mGameSession.getCurrentRoom().roomId

    private fun getModifiedRoom(room: Room): Room{
        room.apply {
            sortPlayers()
            updateCurrentPlayer()
            findAndMarkPhoneOwner(mUserSession.getUserId())
            updateLocallyCreated(mUserSession.getUserId())
        }
        return room
    }

}