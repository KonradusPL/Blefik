package com.konradpekala.blefik.domain.interactors.game

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.gamesession.GameSession
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.repository.room.RoomsRepository
import com.konradpekala.blefik.domain.interactors.base.CompletableUseCase
import com.konradpekala.blefik.domain.interactors.game.errors.BidNotHigher
import com.konradpekala.blefik.domain.interactors.game.errors.BidNotProperlyCreated
import com.konradpekala.blefik.utils.BidUtils
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class UpdateBidUseCase @Inject constructor(@Named("onSubscribe") subscribeScheduler: Scheduler,
                                           @Named("onObserve") observeScheduler: Scheduler,
                                           private val mGameSession: GameSession,
                                           private val mRoomsRepository: RoomsRepository,
                                           private val mUserSession: UserSession
): CompletableUseCase<Bid>(subscribeScheduler, observeScheduler) {

    override fun buildUseCaseCompletable(request: Bid?): Completable {
        val newBid = request!!
        val room = mGameSession.getCurrentRoom().copy()

        if(!BidUtils.isBidProperlyCreated(newBid))
            return Completable.error(BidNotProperlyCreated())
        if (!BidUtils.isNewBidHigher(newBid,room.currentBid))
            return Completable.error(BidNotHigher())


        return mRoomsRepository.updateBid(newBid,room.roomId)
    }

}