package com.konradpekala.blefik.data.repository.room

import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import io.reactivex.Completable
import io.reactivex.Observable

interface IRoomsRepository {
    interface Remote{
        fun addRoom(room: Room): Completable
        fun addPlayerToRoom(player: Player, roomId: String): Completable
        fun changeRoomToStarted(room: Room): Completable
        fun observeRooms(): Observable<Room>
        fun clean()
    }
}