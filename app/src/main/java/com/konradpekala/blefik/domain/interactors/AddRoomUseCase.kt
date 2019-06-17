package com.konradpekala.blefik.domain.interactors

import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.manager.RoomManager
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.utils.schedulers.OnObserveScheduler
import com.konradpekala.blefik.utils.schedulers.OnSubscribeScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddRoomUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                         @Named("onObserve") observeScheduler: Scheduler,
                                         private val mRoomsRepository: RoomsRepository,
                                         private val mRoomManager: RoomManager,
                                         private val mPrefs: Preferences,
                                         private val mAuth: Auth

): CompletableUseCase<String>(subscribeScheduler, observeScheduler) {


    override fun buildUseCaseCompletable(request: String?): Completable {
        val creator = mPrefs.getUserNick()
        val creatorId = mAuth.getUserId()
        val imageUrl = mPrefs.getProfileImageUrl()
        val player = Player(creatorId,creator,imageUrl)
        val room = Room(name = request!!, creatorId = creatorId,createdTime = Timestamp.now())
        room.players.add(player)

        mRoomManager.updateCurrentRoom(room)

        return mRoomsRepository.addRoom(room)

    }
}