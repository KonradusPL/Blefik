package com.konradpekala.blefik.data.database

import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Database {

    fun addUser(user: User): Completable

    fun addRoom(room: Room): Single<String>
    fun addPlayerToRoom(player: Player, roomId: String): Completable
    fun observeRooms(): Observable<Room>
    fun changeRoomToStarted(room: Room): Completable

    fun clean()
}