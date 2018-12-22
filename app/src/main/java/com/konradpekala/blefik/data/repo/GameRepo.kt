package com.konradpekala.blefik.data.repo

import android.util.Log
import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.UpdateType
import com.konradpekala.blefik.data.preferences.SharedPrefs
import com.konradpekala.blefik.utils.CardsGenerator
import com.konradpekala.blefik.utils.PhoneStuff
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable

class GameRepo(val db: Database,val cardsGenerator: CardsGenerator, val prefs: SharedPrefs, val phoneStuff: PhoneStuff) {

    fun observeRoom(id: String): Observable<Room>{
        return db.observeRoom(id)
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
}