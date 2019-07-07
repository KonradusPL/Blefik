package com.konradpekala.blefik.domain.interactors.game

import android.util.Log
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.domain.interactors.base.SynchronizedUseCase
import javax.inject.Inject

class CleanGameSessionUseCase @Inject constructor(private val mGameSession: GameSession)
    : SynchronizedUseCase<Unit, Unit> {

    private val TAG = "CleanGameSessionUseCase"

    override fun execute(request: Unit?) {
        Log.d(TAG,"execute")

        mGameSession.cleanCache()
    }
}