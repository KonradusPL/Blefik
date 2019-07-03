package com.konradpekala.blefik.data.repository.room

import android.util.Log
import com.google.firebase.Timestamp
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.Status
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomsRepository @Inject constructor(private val mRemote: IRoomsRepository.Remote) {


    fun addRoom(room: Room): Completable{
        return mRemote.addRoom(room)
    }

    fun observeAllRooms(maxLifeLengthInMinutes: Int): Observable<Room> {
        return mRemote.observeRooms().filter { room: Room ->
             getRoomLifeLengthInMinutes(room)< maxLifeLengthInMinutes }
    }

    private fun getRoomLifeLengthInMinutes(room: Room): Long{
        Log.d("RoomLifeLength",room.createdTime!!.toString())
        return (Timestamp.now().seconds - room.createdTime!!.seconds)/60
    }
    fun observeRoom(id: String): Observable<Room>{
        return mRemote.observeRoom(id)
    }

    fun addPlayerToRoom(room: Room, player: Player): Completable{
        return mRemote.addPlayerToRoom(player, room.roomId)
    }

    fun changeRoomToStarted(room: Room): Completable{
        return mRemote.changeRoomToStarted(room)
    }

    fun updateRoom(room: Room): Completable = mRemote.updateRoom(room)

    fun updateBid(bid: Bid, roomId: String) = mRemote.updateBid(bid, roomId)
}