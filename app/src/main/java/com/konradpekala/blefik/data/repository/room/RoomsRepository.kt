package com.konradpekala.blefik.data.repository.room

import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomsRepository @Inject constructor(private val prefs: Preferences,
                                          private val userSession: UserSession,
                                          private val mRemote: IRoomsRepository.Remote) {


    fun addRoom(room: Room): Completable{
        return mRemote.addRoom(room)
    }

    fun observeRooms(maxLifeLengthInSeconds: Int): Observable<Room> {
        return mRemote.observeRooms().filter { room: Room ->
            (Timestamp.now().seconds - room.createdTime!!.seconds)/60 < maxLifeLengthInSeconds }
    }

    fun addPlayerToRoom(room: Room, player: Player): Completable{
        return mRemote.addPlayerToRoom(player, room.roomId)
    }

    fun changeRoomToStarted(room: Room): Completable{
        return mRemote.changeRoomToStarted(room)
    }
}