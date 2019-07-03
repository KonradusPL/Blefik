package com.konradpekala.blefik.domain.interactors.room

import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddRoomUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                         @Named("onObserve") observeScheduler: Scheduler,
                                         private val mRoomsRepository: RoomsRepository,
                                         private val mGameSession: GameSession,
                                         private val mUserRepository: UserRepository,
                                         private val mUserSession: UserSession

): CompletableUseCase<String>(subscribeScheduler, observeScheduler) {


    override fun buildUseCaseCompletable(request: String?): Completable {
        val player = mUserRepository.getLocalPlayer()
        val room = makeRoom(request!!)

        room.players.add(player)

        mGameSession.updateCurrentRoom(room)

        return mRoomsRepository.addRoom(room)
    }

    private fun makeRoom(roomName: String): Room{
        val roomCreatorId = mUserSession.getUserId()
        return Room(name = roomName, creatorId = roomCreatorId, createdTime = Timestamp.now())
    }
}