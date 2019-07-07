package com.konradpekala.blefik.domain.interactors.game

import android.util.Log
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class UpdateWinnerDataUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                                 @Named("onObserve") observeScheduler: Scheduler,
                                                 private val mGameSession: GameSession,
                                                 private val mUserRepository: UserRepository
)
    : CompletableUseCase<Unit>(subscribeScheduler,observeScheduler) {

    private val TAG = "UpdateWinnerDataUseCase"

    override fun buildUseCaseCompletable(request: Unit?): Completable {
        Log.d(TAG,"buildUseCaseCompletable")

        val winnerId = mGameSession.getWinnerId()

        return mUserRepository.getGamesWon(winnerId)
            .flatMapCompletable { gamesWon: Int -> mUserRepository.updateGamesWon(winnerId,gamesWon+1) }
    }
}