package com.konradpekala.blefik.domain.interactors.room

import android.util.Log
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

//how old room we are observing [in seconds]
const val MAX_ROOM_LIFE_LENGTH = 15

class ObserveRoomsUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                              @Named("onObserve") observeScheduler: Scheduler,
                                              private val mRoomsRepository: RoomsRepository,
                                              private val mGameSession: GameSession,
                                              private val mUserSession: UserSession)
    : ObservableUseCase<Unit, Room>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseObservable(params: Unit?): Observable<Room> {
        return mRoomsRepository.observeAllRooms(MAX_ROOM_LIFE_LENGTH)
            .map { room: Room -> getModifiedRoom(room) }
    }

    private fun getModifiedRoom(room: Room): Room{
        val isRoomChosenByPlayer = mGameSession.hasSameRoomAs(room)
        if (isRoomChosenByPlayer){
            room.setIsChosenByPlayer(isRoomChosenByPlayer)
            mGameSession.updateStartedRoom(room)
        }

        room.updateLocallyCreated(mUserSession.getUserId())

        Log.d("ObserveRoomsUseCase","$room")

        return room
    }
}