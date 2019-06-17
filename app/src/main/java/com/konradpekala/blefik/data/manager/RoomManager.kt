package com.konradpekala.blefik.data.manager

import com.konradpekala.blefik.data.model.Room
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomManager @Inject constructor( ){

    val isGameStarted = false
    val isLocallyCreated = false

    private var mChoosenRoom: Room = Room()

    fun checkIfRoomIsChoosen(room: Room): Boolean{
        return mChoosenRoom.name==room.name && mChoosenRoom.creatorId == room.creatorId
    }

    fun updateCurrentRoom(room: Room){
        mChoosenRoom = room
    }

    fun hasSameRoomAs(room: Room): Boolean{
        return mChoosenRoom.roomId == room.roomId && mChoosenRoom.name == room.name
    }
}