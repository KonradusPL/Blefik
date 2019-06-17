package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.manager.RoomManager
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.ObservableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

//how old room we are observing [in seconds]
const val MAX_ROOM_LIFE_LENGTH = 15

class ObserveRoomsUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                              @Named("onObserve") observeScheduler: Scheduler,
                                              private val mRoomsRepository: RoomsRepository,
                                              private val mRoomManager: RoomManager,
                                              private val mAuth: Auth)
    : ObservableUseCase<Unit, Room>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseObservable(params: Unit?): Observable<Room> {
        return mRoomsRepository.observeRooms(MAX_ROOM_LIFE_LENGTH)
            .map { room: Room -> room.updateLocallyCreated(mAuth.getUserId()) }
            .map { room: Room -> room.updateIsChoosenByPlayer(mRoomManager.checkIfRoomIsChoosen(room)) }
            .doOnNext { room: Room -> if (room.isChoosenByPlayer) mRoomManager.updateCurrentRoom(room) }
    }
}