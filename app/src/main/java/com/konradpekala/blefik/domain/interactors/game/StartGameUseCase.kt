package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.domain.interactors.base.SynchronizedUseCase
import javax.inject.Inject

class StartGameUseCase @Inject constructor(private val mGameSession: GameSession): SynchronizedUseCase<Unit,Unit> {
    override fun execute(request: Unit?) {
        mGameSession.setGameToStarted()
    }
}