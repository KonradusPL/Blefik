package com.konradpekala.blefik.data.model.room

import com.konradpekala.blefik.data.model.SortByNick
import java.util.*

class RoomModificator(val room: Room) {

    fun sortPlayer(): RoomModificator{
        Collections.sort(room.players, SortByNick())
        return this
    }

    fun markCurrentPlayer(): RoomModificator {
        val indexOfCurrentPlayer = room.currentPlayer
        room.players[indexOfCurrentPlayer].isCurrentPlayer = true
        return this
    }
}