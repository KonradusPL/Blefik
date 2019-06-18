package com.konradpekala.blefik.utils

import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameUtils @Inject constructor(){

    fun isPlayerInRoom(room: Room, player: Player): Boolean{
        for(p in room.players){
            if(p.id == player.id)
                return true
        }
        return false
    }
}