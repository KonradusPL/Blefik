package com.konradpekala.blefik.data.gamesession

import android.util.Log
import com.konradpekala.blefik.data.model.Card
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.UpdateType
import com.konradpekala.blefik.domain.model.CheckBidResponse
import com.konradpekala.blefik.utils.CardsUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameSession @Inject constructor( ){

    private val TAG = "GameSession"

    private var isGameFinished = false

    private var mChoosenRoom: Room? = null

    fun updateStartedRoom(room: Room){
            mChoosenRoom = room.copy()
    }

    fun setGameToFinished(){
        isGameFinished = true
    }

    fun setGameToStarted(){
        isGameFinished = false
    }

    fun hasSameRoomAs(room: Room): Boolean{
        Log.d(TAG,"hasSameRoomAs: is room null: ${mChoosenRoom == null}")
        return if(mChoosenRoom != null)
            mChoosenRoom!!.name == room.name
        else false
    }

    fun cleanCache(){
        mChoosenRoom = null
    }

    fun getCurrentRoom(): Room = mChoosenRoom!!

    fun hasRoom() = mChoosenRoom != null

    fun isBidCreated(): Boolean = mChoosenRoom?.currentBid != null ?: false

    fun getAllUsersCards(): ArrayList<Card>{
        mChoosenRoom?.let { room: Room ->
            val cardsList = ArrayList<Card>()
            for(player in room.players){
                for(card in player.currentCards){
                    cardsList.add(card)
                }
            }
            return cardsList
        }
        return arrayListOf()
    }

    fun addCardToLoser(response: CheckBidResponse){
        mChoosenRoom!!.let { room: Room ->
            val loserIndex: Int = if(response == CheckBidResponse.BID_NOT_IN_CARDS){
                getIdOfPreviousPlayer()
            }else{
                room.currentPlayer
            }
            room.players[loserIndex].cardsCount++
        }
    }

    fun removeBeatenFromCache(player: Player){
        mChoosenRoom!!.players.remove(player)
    }

    fun doesGameNeedToEnd(): Boolean{
        return mChoosenRoom!!.players.size <=1
    }

    fun getWinnerId() = mChoosenRoom!!.players[0].id

    fun findSomeoneBeaten(): Player?{
        for(player in mChoosenRoom!!.players){
            if(player.cardsCount > CardsUtils.maxCardNumber)
                return player
        }
        return null
    }

    private fun getIdOfPreviousPlayer(): Int{
        mChoosenRoom?.let { room: Room ->
            return  if (room.currentPlayer == 0) room.players.size-1
            else room.currentPlayer-1
        }
        return -1
    }

}