package com.konradpekala.blefik.domain.interactors.room

import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.manager.GameSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class AddRoomUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                         @Named("onObserve") observeScheduler: Scheduler,
                                         private val mRoomsRepository: RoomsRepository,
                                         private val mGameSession: GameSession,
                                         private val mPrefs: Preferences,
                                         private val mUserSession: UserSession

): CompletableUseCase<String>(subscribeScheduler, observeScheduler) {


    override fun buildUseCaseCompletable(request: String?): Completable {
        val creator = mPrefs.getUserNick()
        val creatorId = mUserSession.getUserId()
        val imageUrl = mPrefs.getProfileImageUrl()
        val player = Player(creatorId,creator,imageUrl)
        val room = Room(name = request!!, creatorId = creatorId,createdTime = Timestamp.now())
        room.players.add(player)

        mGameSession.updateCurrentRoom(room)

        return mRoomsRepository.addRoom(room)

    }
}