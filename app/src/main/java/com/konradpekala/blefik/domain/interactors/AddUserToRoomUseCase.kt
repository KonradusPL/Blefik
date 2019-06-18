package com.konradpekala.blefik.domain.interactors

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.manager.RoomManager
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.error_models.PlayerIsInRoom
import com.konradpekala.blefik.domain.error_models.SameRoom
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.GameUtils
import com.konradpekala.blefik.utils.SchedulerProvider
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddUserToRoomUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                               @Named("onObserve") observeScheduler: Scheduler,
                                               private val mUserRepository: UserRepository,
                                               private val mRoomManager: RoomManager,
                                               private val mGameUtils: GameUtils,
                                               private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Room>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(room: Room?): Completable {
        val player = mUserRepository.getLocalPlayer()

        if(mRoomManager.hasSameRoomAs(room!!))
            return Completable.error(SameRoom())

        if (mGameUtils.isPlayerInRoom(room,player)){
            room.isChoosenByPlayer = true
            mRoomManager.updateCurrentRoom(room)
            return Completable.complete()
        }


        return mRoomsRepository.addPlayerToRoom(room, player)
            .doOnComplete { mRoomManager.updateCurrentRoom(room) }
    }

}