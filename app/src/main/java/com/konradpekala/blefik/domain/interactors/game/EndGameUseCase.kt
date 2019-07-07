package com.konradpekala.blefik.domain.interactors.game

import android.util.Log
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.base.SynchronizedUseCase
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class EndGameUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                         @Named("onObserve") observeScheduler: Scheduler,
                                         private val mUpdateWinnerDataUseCase: UpdateWinnerDataUseCase,
                                         private val mCleanGameSessionUseCase: CleanGameSessionUseCase,
                                         private val mRoomsRepository: RoomsRepository,
                                         private val mGameSession: GameSession
)
    : CompletableUseCase<Unit>(subscribeScheduler,observeScheduler) {

    private val TAG = "EndGameUseCase"

    override fun buildUseCaseCompletable(request: Unit?): Completable {
        Log.d(TAG,"buildUseCaseCompletable")

        val room = mGameSession.getCurrentRoom().copy()
        room.updateType = UpdateType.GameEnded

        return mUpdateWinnerDataUseCase.raw()
            .andThen(Completable.defer { mRoomsRepository.updateRoom(room) })
            .doOnComplete { mCleanGameSessionUseCase.execute() }
    }
}