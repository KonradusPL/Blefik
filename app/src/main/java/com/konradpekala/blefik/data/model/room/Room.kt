package com.konradpekala.blefik.data.model.room

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.SortByNick
import java.util.*

data class Room(
    var name: String = "",
    var creatorId: String = "",
    var updateType: UpdateType = UpdateType.None,
    var currentPlayer: Int = 0,
    var currentBid: Bid? = null,
    var createdTime: Timestamp? = null,
    @get:Exclude var isChoosenByPlayer: Boolean = false,
    @get:Exclude var roomId: String = "",
    @get:Exclude var status: Status = Status.Added,
    @get:Exclude var locallyCreated: Boolean = false,
    var hasGameStarted: Boolean = false,
    var players: ArrayList<Player> = ArrayList()){

    constructor(room: Room) : this() {
        name = room.name
        creatorId = room.creatorId
        updateType = room.updateType
        currentPlayer = room.currentPlayer
        currentBid = room.currentBid
        hasGameStarted = room.hasGameStarted
        players = ArrayList(room.players)
        roomId = room.roomId
    }


    fun playerMap(): ArrayList<HashMap<String,Any>>{
        val arrayList = ArrayList<HashMap<String,Any>>()
        for(player in players){
            val map = player.map()
            arrayList.add(map)
        }
        return arrayList
    }

    fun updateCardsForGivenPlayer(player: Player){
        for (p in players){
            if (player.id == p.id)
                player.currentCards = p.currentCards
        }
    }

    fun sortPlayers() {
        Collections.sort(players, SortByNick())
    }

    fun updateCurrentPlayer(){
        if(currentPlayer < players.size && currentPlayer >= 0)
            players[currentPlayer].isCurrentPlayer = true
    }

    fun updateLocallyCreated(creatorId: String) {
        if(this.creatorId == creatorId)
            locallyCreated = true
    }

    fun setIsChosenByPlayer(value: Boolean) {
        isChoosenByPlayer = value
    }

    fun findAndMarkPhoneOwner(userId: String) {
        for(player in players){
            if (player.id == userId)
                player.phoneOwner = true
        }
    }

}
enum class Status{Added,Changed,Removed}
enum class UpdateType{NewGame,NewBid,NextPlayer,PlayerBeaten,None}