package com.konradpekala.blefik.domain.interactors.auth

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.manager.GameSession
import com.konradpekala.blefik.data.model.request.LoginRequest
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.image.ImageRepository
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.base.SynchronousUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class LogOutUseCase @Inject constructor(private val mUserSession: UserSession,
                                        private val mPreferences: Preferences,
                                        private val mImageRepository: ImageRepository,
                                        private val mGameSession: GameSession
): SynchronousUseCase<Unit,Unit> {


    override fun execute(request: Unit?) {
        mUserSession.logOut()
        mPreferences.removeUser()
        mImageRepository.cleanCache()
        mGameSession.cleanCache()
    }
}