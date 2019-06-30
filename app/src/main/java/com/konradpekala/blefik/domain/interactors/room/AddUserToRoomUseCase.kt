package com.konradpekala.blefik.domain.interactors.room

import com.konradpekala.blefik.data.manager.GameSession
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.error_models.SameRoom
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.GameUtils
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddUserToRoomUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                               @Named("onObserve") observeScheduler: Scheduler,
                                               private val mUserRepository: UserRepository,
                                               private val mGameSession: GameSession,
                                               private val mGameUtils: GameUtils,
                                               private val mRoomsRepository: RoomsRepository
): CompletableUseCase<Room>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(room: Room?): Completable {
        val player = mUserRepository.getLocalPlayer()

        if(mGameSession.hasSameRoomAs(room!!))
            return Completable.error(SameRoom())

        if (mGameUtils.isPlayerInRoom(room,player)){
            room.isChoosenByPlayer = true
            mGameSession.updateCurrentRoom(room)
            return Completable.complete()
        }


        return mRoomsRepository.addPlayerToRoom(room, player)
            .doOnComplete { mGameSession.updateCurrentRoom(room) }
    }

}