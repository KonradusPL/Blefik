package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.Room
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameUtils @Inject constructor(){

    fun isPlayerInRoom(room: Room, playerId: String): Boolean{
        for(player in room.players){
            if(player.id == playerId)
                return true
        }
        return false
    }
}