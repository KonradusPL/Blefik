package com.konradpekala.blefik.data.database

import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.user.User
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface Database {

    fun addUser(user: User): Completable

    fun addRoom(room: Room): Single<String>
    fun addPlayerToRoom(player: Player, roomId: String): Completable
    fun observeRooms(): Observable<Room>
    fun changeRoomToStarted(room: Room): Completable

    fun observeRoom(id: String): Observable<Room>
    fun updateRoom(room: Room): Completable
    fun updateBid(room: Room): Completable

    fun getUserGamesWon(id: String): Single<Int>
    fun updateUserGamesWon(id: String, gamesWon: Int): Completable

    fun getUsers(): Single<List<User>>

    fun clean()
}