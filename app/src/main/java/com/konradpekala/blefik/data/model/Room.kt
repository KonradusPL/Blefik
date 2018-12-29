package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude
import java.util.*

class Room(
    var name: String = "",
    var creatorId: String = "",
    var updateType: UpdateType = UpdateType.None,
    var currentPlayer: Int = 0,
    var currentBid: Bid? = null,
    @get:Exclude var isLoading: Boolean = false,
    @get:Exclude var roomId: String = "",
    @get:Exclude var status: Status = Status.Added,
    @get:Exclude var locallyCreated: Boolean = false,
    var started: Boolean = false,
    var players: ArrayList<Player> = ArrayList()
                ){

    fun toMap(): HashMap<String,Any>{
        val hashMap = HashMap<String,Any>()
        hashMap["name"] = name
        hashMap["creatorId"] = creatorId
        hashMap["updateType"] = updateType
        hashMap["currentPlayer"] = currentPlayer
        hashMap["started"] = started
        hashMap["players"] = players
        return hashMap
    }
    override fun toString(): String {
        return "name: $name, status: $status, isLoading: $isLoading"
    }

    constructor(room: Room) : this() {
        name = room.name
        creatorId = room.creatorId
        updateType = room.updateType
        currentPlayer = room.currentPlayer
        started = room.started
        players = ArrayList(room.players)
        roomId = room.roomId
    }

    fun hasPlayer(newPlayer: Player): Boolean{
        var value = false
        for (player in players){
            if(player.id == newPlayer.id)
                value = true
        }
        return value
    }

    fun isEqualTo(newRoom: Room): Boolean{
        return name==newRoom.name && creatorId == newRoom.creatorId
    }

    fun playerMap(): ArrayList<HashMap<String,Any>>{
        val arrayList = ArrayList<HashMap<String,Any>>()
        for(player in players){
            val map = player.map()
            arrayList.add(map)
        }
        return arrayList
    }

    fun sortPlayers(): Room{
        Collections.sort(players,SortByNick())
        return this
    }

    fun updateCurrentPlayer(): Room{
        if(currentPlayer < players.size && currentPlayer >= 0)
            players[currentPlayer].isCurrentPlayer = true
        return this
    }

    fun updatePhoneOwner(phoneId: String): Room{
        for(player in players){
            if (player.id == phoneId)
                player.phoneOwner = true
        }
        return this
    }

}
enum class Status{Added,Changed,Removed}
enum class UpdateType{NewGame,NewBid,PlayerChanged,None}