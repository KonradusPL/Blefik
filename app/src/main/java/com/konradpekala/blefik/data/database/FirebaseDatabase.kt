package com.konradpekala.blefik.data.database

import android.util.Log
import com.google.firebase.firestore.*
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.Room
import com.konradpekala.blefik.data.model.User
import io.reactivex.*

class FirebaseDatabase: Database {

    private val database = FirebaseFirestore.getInstance()

    private val settings = FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build()

    init {
        database.firestoreSettings = settings
    }

    private var mRoomsListener: ListenerRegistration? = null

    override fun addUser(user: User): Completable {
        return Completable.create{emitter ->
            database.collection("users").add(user)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else{
                        if(task.exception != null)
                            emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun addRoom(room: Room): Single<String> {
        return Single.create{emitter: SingleEmitter<String> ->
            database.collection("rooms").add(room)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        emitter.onSuccess("success")
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun addPlayerToRoom(player: Player, roomId: String): Completable {
        return Completable.create{emitter ->
            database.collection("rooms")
                .document(roomId)
                .update("players",FieldValue.arrayUnion(player))
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
                            emitter.onNext(room)
                        }
                        DocumentChange.Type.REMOVED -> {
                            emitter.onNext(room)
                        }
                        DocumentChange.Type.MODIFIED ->{
                            emitter.onNext(room)
                        }
                    }
                }
            }
        }
    }

    override fun clean() {
        mRoomsListener?.remove()
    }


}