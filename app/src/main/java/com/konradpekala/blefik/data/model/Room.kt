package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude

class Room(
    var name: String = "",
    var creatorId: String = "",
    var updateType: UpdateType = UpdateType.None,
    var currentPlayer: Int = 0,
    @get:Exclude var isLoading: Boolean = false,
    @get:Exclude var roomId: String = "",
    @get:Exclude var status: Status = Status.Added,
    @get:Exclude var locallyCreated: Boolean = false,
    var started: Boolean = false,
    val players: ArrayList<Player> = ArrayList()
                ){

    fun getMap(): HashMap<String,Any>{
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

}
enum class Status{Added,Changed,Removed}
enum class UpdateType{NewGame,NewBid,PlayerChanged,None}