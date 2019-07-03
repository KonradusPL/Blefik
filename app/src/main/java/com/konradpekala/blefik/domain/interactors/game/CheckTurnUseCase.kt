package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.domain.model.CheckBidResponse
import com.konradpekala.blefik.domain.model.CheckTurnResponse
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
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

    private var mPlayerRemoved: Player? = null
    private var mIsSomeoneRemoved = false
    private var mDoesGameNeedToEnd = false
    private lateinit var mCheckBidResponse: CheckBidResponse

    override fun buildUseCaseSingle(request: Unit?): Single<CheckTurnResponse> {
        return mCheckBidUseCase.raw()
            .doOnSuccess {  response: CheckBidResponse -> onCheckBidSuccess(response) }
            .flatMapCompletable { removePlayerIfNecessary()}
            .andThen(endGameIfNecessary())
            .toSingle { getCheckTurnResponse() }
    }

    private fun onCheckBidSuccess(response: CheckBidResponse){
        mCheckBidResponse = response
        mGameSession.addCardToLoser(response)
    }

    private fun removePlayerIfNecessary(): Completable{
        if(isNeedForRemovingPlayer()) {
            mPlayerRemoved = mGameSession.findSomeoneBeaten()
            mIsSomeoneRemoved = true
            return mRemovePlayerUseCase.raw()
        }
        else return Completable.complete()
    }

    private fun endGameIfNecessary(): Completable{
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