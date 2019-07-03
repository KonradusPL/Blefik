package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.domain.model.EndGameIfNecessaryResponse
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class EndGameUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                         @Named("onObserve") observeScheduler: Scheduler,
                                         private val mGameSession: GameSession,
                                         private val mUserRepository: UserRepository
)
    : CompletableUseCase<Unit>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseCompletable(request: Unit?):Completable {
        val winnerId = mGameSession.getWinnerId()

        mGameSession.cleanCache()

        return mUserRepository.getGamesWon(winnerId)
            .flatMapCompletable { gamesWon: Int -> mUserRepository.updateGamesWon(winnerId,gamesWon+1) }
    }
}