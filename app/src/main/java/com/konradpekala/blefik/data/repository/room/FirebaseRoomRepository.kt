package com.konradpekala.blefik.data.repository.room

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.konradpekala.blefik.data.model.Bid
import com.konradpekala.blefik.data.model.Player
import com.konradpekala.blefik.data.model.room.Room
import com.konradpekala.blefik.data.model.room.Status
import com.konradpekala.blefik.data.model.room.UpdateType
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

    override fun observeRoom(id: String): Observable<Room> {
        return Observable.create { emitter: ObservableEmitter<Room> ->
            database.collection("rooms").document(id).addSnapshotListener{dc, exception ->
                if (dc == null){
                    emitter.onError(Throwable())
                    return@addSnapshotListener
                }
                val room = dc.toObject(Room::class.java)
                room?.roomId = id
                if(room != null)
                    emitter.onNext(room)
                else
                    emitter.onError(Throwable("room is a null!"))
            }
        }
    }

    override fun observeRooms(): Observable<Room> {
        return Observable.create { emitter: ObservableEmitter<Room> ->
            mRoomsListener = database.collection("rooms").addSnapshotListener { querySnapshot: QuerySnapshot?,
                                                                                exception: FirebaseFirestoreException? ->
                Log.d("observeAllRooms",exception.toString())
                Log.d("observeAllRooms",querySnapshot!!.documentChanges.size.toString())
                for (dc in querySnapshot.documentChanges) {
                    val id = dc.document.id
                    val room = dc.document.toObject(Room::class.java)
                    room.roomId = id
                    Log.d("observeAllRooms",room.toString())
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
                .update("hasGameStarted",true)
                .addOnCompleteListener { task: Task<Void> ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun updateBid(bid: Bid, roomId: String): Completable {
        return Completable.create { emitter ->

            database.collection("rooms")
                .document(roomId)
                .update("currentBid",bid.map(),"updateType", UpdateType.NewBid.name)
                .addOnCompleteListener { task: Task<Void> ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun updateRoom(room: Room): Completable {
        return Completable.create{emitter ->

            val document = database.collection("rooms")
                .document(room.roomId)


            val request = when(room.updateType){
                UpdateType.NextPlayer -> document.update("updateType",room.updateType.name,
                    "currentPlayer",room.currentPlayer)

                UpdateType.PlayerBeaten -> document.update("updateType",room.updateType.name,
                    "players",room.playerMap())

                UpdateType.NewGame -> document.update("players",room.playerMap(),
                    "updateType",room.updateType.name,
                    "currentPlayer",room.currentPlayer,
                    "currentBid", room.currentBid?.map())

                UpdateType.NewBid -> document.update("players",room.playerMap(),
                    "updateType",room.updateType.name,
                    "currentPlayer",room.currentPlayer,
                    "currentBid", room.currentBid?.map())

                else -> document.update("updateType",room.updateType.name)
            }

            request.addOnCompleteListener { task: Task<Void> ->
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