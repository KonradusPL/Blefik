package com.konradpekala.blefik.domain.interactors.add_user_to_room

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
                                               private val mAuth: Auth,
                                               private val mUserRepository: UserRepository,
                                               private val mRoomManager: RoomManager,
                                               private val mGameUtils: GameUtils,
                                               private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Room>(subscribeScheduler, observeScheduler) {


    override fun buildUseCaseCompletable(request: Room?): Completable {
        if(mRoomManager.hasSameRoomAs(request!!))
            return Completable.error(SameRoom())

        if (mGameUtils.isPlayerInRoom(request,mAuth.getUserId()))
            return Completable.error(PlayerIsInRoom())

        val player = mUserRepository.getLocalPlayer()

        return mRoomsRepository.addPlayerToRoom(request, player)
            .doOnComplete { mRoomManager.updateCurrentRoom(request) }
    }
}