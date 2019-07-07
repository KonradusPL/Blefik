package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.repository.users.UserRepository
import com.konradpekala.blefik.domain.interactors.base.SingleUseCase
import com.konradpekala.blefik.domain.interactors.game.errors.BidNotCreated
import com.konradpekala.blefik.domain.model.CheckBidResponse
import com.konradpekala.blefik.utils.CardsUtils
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class CheckBidUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                          @Named("onObserve") observeScheduler: Scheduler,
                                          private val mGameSession: GameSession
)
    : SingleUseCase<Unit, CheckBidResponse>(subscribeScheduler,observeScheduler) {

    override fun buildUseCaseSingle(request: Unit?): Single<CheckBidResponse> {
        if (!mGameSession.isBidCreated())
            return Single.error(BidNotCreated())

        val currentBid = mGameSession.getCurrentRoom().currentBid ?: Bid()
        val usersCards = mGameSession.getAllUsersCards()

        val isBidInCards = CardsUtils.isBidInCards(usersCards,currentBid)


        return if(isBidInCards) Single.just(CheckBidResponse.BID_IN_CARDS)
        else Single.just(CheckBidResponse.BID_NOT_IN_CARDS)

    }

}
