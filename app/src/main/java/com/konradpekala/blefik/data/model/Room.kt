package com.konradpekala.blefik.data.model

import com.google.firebase.firestore.Exclude

class Room(
    var name: String = "",
    var creatorId: String = "",
    @get:Exclude var isLoading: Boolean = false,
    @get:Exclude var roomId: String = "",
    @get:Exclude var status: Status = Status.Added,
    @get:Exclude var locallyCreated: Boolean = false,
    var started: Boolean = false,
    val players: ArrayList<Player> = ArrayList()
                ){
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
}
enum class Status{Added,Changed,Removed}