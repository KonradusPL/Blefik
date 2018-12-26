package com.konradpekala.blefik.data.repo

import android.util.Log
import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.UpdateType
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.utils.CardsGenerator
import com.konradpekala.blefik.utils.PhoneStuff
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable

class GameRepo(val db: Database,val cardsGenerator: CardsGenerator, val prefs: SharedPrefs, val phoneStuff: PhoneStuff) {

    private var mRoom: Room? = null

    fun observeRoom(id: String): Observable<Room>{
        return db.observeRoom(id)
            .map { t: Room -> t.sortPlayers().updateCurrentPlayer()
            }
            .doOnNext { room: Room? ->
                Log.d("onNext vs doOnNext","doOnNext")
                mRoom = room
            }
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun makeNewRound(room: Room): Completable{
        room.updateType = UpdateType.NewGame
        cardsGenerator.cardsForNewRound(room.players,true)
        Log.d("whereIsArray",room.players.toString())

        return db.updateRoom(room)
            .subscribeOn(SchedulerProvider.io())
            .observeOn(SchedulerProvider.ui())
    }

    fun getPlayer(): Player? {
        if (mRoom == null)
            return null

        for(player in mRoom!!.players){
            if(player.id == phoneStuff.getAndroidId())
                return player
        }
        return null
    }

    fun playerIsCreator(): Boolean{
        return getPlayer()!!.id == mRoom!!.creatorId
    }
}