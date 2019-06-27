package com.konradpekala.blefik.data.repository.room

import android.util.Log
import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomsRepository @Inject constructor(private val prefs: Preferences,
                                          private val userSession: FirebaseUserSession,
                                          private val mRemote: IRoomsRepository.Remote) {

    private var mCurrentRoom: Room? = null

    /*fun addRoom(name: String): Single<String> {
        val creator = prefs.getUserNick()
        val creatorId = userSession.getUserId()
        val imageUrl = prefs.getProfileImageUrl()
        val player = Player(creatorId,creator,imageUrl)
        mCurrentRoom = Room(name = name, creatorId = creatorId,createdTime = Timestamp.now())
        mCurrentRoom?.players?.add(player)

        return database.addRoom(mCurrentRoom!!)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }*/

    fun addRoom(room: Room): Completable{
        return mRemote.addRoom(room)
    }

    fun observeRooms(maxLifeLengthInSeconds: Int): Observable<Room> {
        return mRemote.observeRooms().filter { room: Room ->
            (Timestamp.now().seconds - room.createdTime!!.seconds)/60 < maxLifeLengthInSeconds }
    }

    fun addPlayerToRoom(room: Room, player: Player): Completable{
        //mCurrentRoom = room
        return mRemote.addPlayerToRoom(player, room.roomId)
    }

    fun changeRoomToStarted(room: Room): Completable{
        return mRemote.changeRoomToStarted(room)
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
        val id = userSession.getUserId()
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