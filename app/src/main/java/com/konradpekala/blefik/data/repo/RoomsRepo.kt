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

    private var mCurrentRoom: Room? = null

    fun addRoom(name: String): Single<String> {
        val creator = prefs.getUserName()
        val creatorId = phoneStuff.getAndroidId()
        val player = Player(creatorId,creator)
        mCurrentRoom = Room(name = name, creatorId = creatorId)
        mCurrentRoom?.players?.add(player)

        return database.addRoom(mCurrentRoom!!)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun observeRooms(): Observable<Room> {
        return database.observeRooms()
            .map { t: Room -> t.updateLocallyCreated(phoneStuff.getAndroidId()) }
            .map { t: Room -> if(mCurrentRoom?.isEqualTo(t) == true) t.updateIsChoosenByPlayer(true) else t}
            .doOnNext { t -> mCurrentRoom = t }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun addUserToRoom(room: Room): Completable{
        val player = getLocalPlayer()

        return database.addPlayerToRoom(player, room.roomId)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun changeRoomToStarted(room: Room): Completable{
        return database.changeRoomToStarted(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun playerIsInRoom(room: Room): Boolean{
        val localPlayer = Player()
        for(player in room.players){
            if(player.id == localPlayer.id)
                return true
        }
        return false
    }

    fun getLocalPlayer(): Player{
        val creator = prefs.getUserName()
        val id = phoneStuff.getAndroidId()
        return Player(id,creator)

    }

    fun isSameAsChosenBefore(room: Room): Boolean{
        if(mCurrentRoom == null)
            return false
        else if(mCurrentRoom!!.roomId == room.roomId && mCurrentRoom!!.name == room.name)
            return true
        return false
    }
}