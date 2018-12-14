package com.konradpekala.blefik.data.repo

import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.utils.PhoneStuff
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class RoomsRepo(var database: Database,val prefs: Preferences,val phoneStuff: PhoneStuff) {
    fun addRoom(room: Room): Single<String> {
        return database.addRoom(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun observeRooms(): Observable<Room> {
        return database.observeRooms()
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun addUserToRoom(player: Player, roomId: String): Completable{
        return database.addPlayerToRoom(player, roomId)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun changeRoomToStarted(room: Room): Completable{
        return database.changeRoomToStarted(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }
}