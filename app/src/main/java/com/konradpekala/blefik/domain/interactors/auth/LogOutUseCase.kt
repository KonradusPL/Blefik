package com.konradpekala.blefik.domain.interactors.auth

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.manager.GameSession
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.data.repository.image.ImageRepository

import com.konradpekala.blefik.domain.interactors.base.SynchronousUseCase

import javax.inject.Inject

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