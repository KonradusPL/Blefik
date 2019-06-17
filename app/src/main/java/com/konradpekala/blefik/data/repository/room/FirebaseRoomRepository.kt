package com.konradpekala.blefik.data.repository.room

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.Status
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject

class FirebaseRoomsRepository @Inject constructor(): IRoomsRepository.Remote {

    private val database = FirebaseFirestore.getInstance()

    private val settings = FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build()

    init {
        database.firestoreSettings = settings
    }

    private var mRoomsListener: ListenerRegistration? = null

    override fun addRoom(room: Room): Completable {
        return Completable.create { emitter ->
            database.collection("rooms").add(room)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }
                    else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun addPlayerToRoom(player: Player, roomId: String): Completable {
        return Completable.create{emitter ->
            val map = HashMap<String,Any>()
            map["id"] = player.id
            map["nick"] = player.nick
            map["currentCards"] = player.currentCards
            map["imageUrl"] = player.imageUrl

            database.collection("rooms")
                .document(roomId)
                .update("players", FieldValue.arrayUnion(map))
                .addOnCompleteListener { task: Task<Void> ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun observeRooms(): Observable<Room> {
        return Observable.create { emitter: ObservableEmitter<Room> ->
            mRoomsListener = database.collection("rooms").addSnapshotListener { querySnapshot: QuerySnapshot?,
                                                                                exception: FirebaseFirestoreException? ->
                Log.d("observeRooms",exception.toString())
                Log.d("observeRooms",querySnapshot!!.documentChanges.size.toString())
                for (dc in querySnapshot!!.documentChanges) {
                    val id = dc.document.id
                    val room = dc.document.toObject(Room::class.java)
                    room.roomId = id
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> {
                            room.status = Status.Added
                            emitter.onNext(room)
                        }
                        DocumentChange.Type.REMOVED -> {
                            room.status = Status.Removed
                            emitter.onNext(room)
                        }
                        DocumentChange.Type.MODIFIED ->{
                            room.status = Status.Changed
                            emitter.onNext(room)
                        }
                    }
                }
            }
        }
    }

    override fun changeRoomToStarted(room: Room): Completable {
        return Completable.create{emitter ->
            database.collection("rooms")
                .document(room.roomId)
                .update("started",true)
                .addOnCompleteListener { task: Task<Void> ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun clean() {
        mRoomsListener?.remove()
    }
}