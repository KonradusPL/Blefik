package com.konradpekala.blefik.data.repo

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.konradpekala.blefik.data.database.Database
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.preferences.Preferences
import com.konradpekala.blefik.utils.PhoneStuff
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
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
}