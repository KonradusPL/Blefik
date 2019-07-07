package com.konradpekala.blefik.domain.interactors.game

import android.util.Log
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.domain.model.CheckBidResponse
import com.konradpekala.blefik.domain.model.CheckTurnResponse
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import javax.inject.Inject
import javax.inject.Named

class CheckTurnUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                           @Named("onObserve") observeScheduler: Scheduler,
                                           private val mCheckBidUseCase: CheckBidUseCase,
                                           private val mRemovePlayerUseCase: RemovePlayerUseCase,
                                           private val mGameSession: GameSession,
                                           private val mEndGameUseCase: EndGameUseCase
)
    : SingleUseCase<Unit, CheckTurnResponse>(subscribeScheduler,observeScheduler) {

    private val TAG = "CheckTurnUseCase"

    private var mPlayerRemoved: Player? = null
    private var mIsSomeoneRemoved = false
    private var mDoesGameNeedToEnd = false
    private lateinit var mCheckBidResponse: CheckBidResponse

    override fun buildUseCaseSingle(request: Unit?): Single<CheckTurnResponse> {
        return mCheckBidUseCase.raw()
            .doOnSuccess {  response: CheckBidResponse -> onCheckBidSuccess(response) }
            .flatMapCompletable { removePlayerIfNecessary()}
            .andThen(Completable.defer{ endGameIfNecessary()} )
            .andThen(Single.fromCallable { getCheckTurnResponse() })
    }

    private fun onCheckBidSuccess(response: CheckBidResponse){
        Log.d(TAG,"onCheckBidSuccess")
        mCheckBidResponse = response
        mGameSession.addCardToLoser(response)
    }

    private fun removePlayerIfNecessary(): Completable{
        Log.d(TAG,"removePlayerIfNecessary")
        if(isNeedForRemovingPlayer()) {
            mPlayerRemoved = mGameSession.findSomeoneBeaten()
            mIsSomeoneRemoved = true
            return mRemovePlayerUseCase.raw(mPlayerRemoved)
        }
        else return Completable.complete()
    }

    private fun endGameIfNecessary(): Completable{
        Log.d(TAG,"endGameIfNecessary")
        if(mGameSession.doesGameNeedToEnd()) {
            mDoesGameNeedToEnd = true
            return mEndGameUseCase.raw()
        }
        else return Completable.complete()
    }

    private fun isNeedForRemovingPlayer() = mGameSession.findSomeoneBeaten() != null

    private fun getCheckTurnResponse(): CheckTurnResponse {
        return CheckTurnResponse(mCheckBidResponse,mPlayerRemoved,mPlayerRemoved != null, mDoesGameNeedToEnd)
    }
}