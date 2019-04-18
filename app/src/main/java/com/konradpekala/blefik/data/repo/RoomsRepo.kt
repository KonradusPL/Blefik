package com.konradpekala.blefik.data.repo

import android.util.Log
import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class RoomsRepo(var database: Database,val prefs: Preferences,val auth: FirebaseAuth) {

    private var mCurrentRoom: Room? = null

    fun addRoom(name: String): Single<String> {
        val creator = prefs.getUserNick()
        val creatorId = auth.getUserId()
        val imageUrl = prefs.getProfileImageUrl()
        val player = Player(creatorId,creator,imageUrl)
        mCurrentRoom = Room(name = name, creatorId = creatorId,createdTime = Timestamp.now())
        mCurrentRoom?.players?.add(player)

        return database.addRoom(mCurrentRoom!!)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun observeRooms(): Observable<Room> {
        return database.observeRooms().filter { t: Room ->
            (Timestamp.now().seconds-t.createdTime!!.seconds)/60 < 10 }
            .map { t: Room -> t.updateLocallyCreated(auth.getUserId()) }
            .doOnNext {
                t: Room? -> Log.d("sekundziki",((Timestamp.now().seconds-t!!.createdTime!!.seconds)/60).toString())
            }
            .map { t: Room -> if(mCurrentRoom?.isEqualTo(t) == true) t.updateIsChoosenByPlayer(true) else t}
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun addUserToRoom(room: Room): Completable{
        val player = getLocalPlayer()
        mCurrentRoom = room

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
        val localPlayer = getLocalPlayer()
        for(player in room.players){
            if(player.id == localPlayer.id)
                return true
        }
        return false
    }

    private fun getLocalPlayer(): Player{
        val creator = prefs.getUserNick()
        val id = auth.getUserId()
        val imageUrl = prefs.getProfileImageUrl()
        Log.d("local player","$creator $id")
        return Player(id,creator,imageUrl)
    }

    fun isSameAsChosenBefore(room: Room): Boolean{
        if(mCurrentRoom == null)
            return false
        else if(mCurrentRoom!!.roomId == room.roomId && mCurrentRoom!!.name == room.name)
            return true
        return false
    }

    fun updateCurrentRoom(room: Room){
        mCurrentRoom = room
    }
}