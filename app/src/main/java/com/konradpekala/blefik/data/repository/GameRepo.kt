package com.konradpekala.blefik.data.repository

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.*
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.utils.CardsUtils
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable

class GameRepo(val db: Database,
               val cardsStuff: CardsUtils,
               val userSession: UserSession) {

    private lateinit var mRoom: Room

    /*fun observeRoom(id: String): Observable<Room>{
        return db.observeRoom(id)
            .map { t: Room -> t.sortPlayers()
                .updateCurrentPlayer()
                .findAndMarkPhoneOwner(userSession.getUserId())
            }
            .doOnNext { room: Room? ->
                Log.d("onNegit xt vs doOnNext","doOnNext")
                mRoom = room!!
            }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }*/

    fun makeNewRound(firstRound: Boolean): Completable{
        val room = mRoom.copy()
        room.updateType = UpdateType.NewGame
        cardsStuff.cardsForNewRound(room.players,firstRound)
        room.currentBid = null

        if(firstRound)
            room.currentPlayer = 0
        else{
            if(room.currentPlayer < room.players.size-1)
                room.currentPlayer++
            else
                room.currentPlayer = 0
        }

        return db.updateRoom(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
    fun makeNextPlayer(): Completable{
        //val room = Room(mRoom)
        val room = mRoom.copy()
        room.updateType = UpdateType.NextPlayer
        if(room.currentPlayer < room.players.size-1)
            room.currentPlayer++
        else
            room.currentPlayer = 0
        return db.updateRoom(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    /*fun updateBid(bid: Bid): Completable{
        //val room = Room(mRoom)
        val room = mRoom.copy()
        room.updateType = UpdateType.NewBid
        room.currentBid = bid

        return db.updateBid(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }*/

    fun getPlayer(): Player? {
        for(player in mRoom!!.players){
            if(player.id == userSession.getUserId())
                return player
        }
        return null
    }


    fun playerIsCreator(): Boolean{
        return getPlayer()!!.id == mRoom.creatorId
    }

    fun generateBidCards(): ArrayList<Card>{
        return cardsStuff.generateCardsForBid(mRoom.currentBid)
    }

    fun isBidCreated() = mRoom.currentBid != null

    fun isBidInCards(): Boolean{
        val cardsList = ArrayList<Card>()
        for(player in mRoom.players){
            for(card in player.currentCards){
                cardsList.add(card)
            }
        }
        return cardsStuff.isBidInCards(cardsList,mRoom.currentBid!!)
    }

    fun addCardToLoser(hasCheckerWon: Boolean){
        val loserIndex: Int
        if(hasCheckerWon){
            loserIndex = if (mRoom.currentPlayer == 0) mRoom.players.size-1
                            else mRoom.currentPlayer-1
        }else{
            loserIndex = mRoom.currentPlayer
        }

        mRoom.players[loserIndex].cardsCount++
    }

    fun removePlayer(player: Player): Completable{
        //val room = Room(mRoom)
        val room = mRoom.copy()
        room.updateType = UpdateType.PlayerBeaten
        room.players.remove(player)

        return db.updateRoom(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun updateGamesWonByWinner(): Completable{
        val winnerId = mRoom.players[0].id
        return db.getUserGamesWon(winnerId)
            .flatMapCompletable { gamesWon: Int ->
                db.updateUserGamesWon(winnerId,gamesWon+1) }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun isSomeoneBeaten(): Player?{
        for(player in mRoom.players){
            if(player.cardsCount > cardsStuff.maxCardNumber)
                return player
        }
        return null
    }

    fun getRoom(): Room?{
        return mRoom
    }
}