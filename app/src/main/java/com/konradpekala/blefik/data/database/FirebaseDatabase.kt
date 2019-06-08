package com.konradpekala.blefik.data.database

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.konradpekala.blefik.data.model.*
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
                .update("players",FieldValue.arrayUnion(map))
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

    override fun updateBid(room: Room): Completable {
        return Completable.create { emitter ->

            database.collection("rooms")
                .document(room.roomId)
                .update("currentBid",room.currentBid?.map(),"updateType",room.updateType.name)
                .addOnCompleteListener { task: Task<Void> ->
                    if(task.isSuccessful){
                        emitter.onComplete()
                    }else if(task.exception != null){
                        emitter.onError(task.exception!!.fillInStackTrace())
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

    fun createUser(email: String,password: String,id: String, nick: String): Completable{
        val user = User(email = email,password = password,id = id,nick = nick)
        return Completable.create { emitter ->
            database.collection("users").document(user.id).set(user)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    fun changeUserNick(id: String, newNick: String): Completable{
        return Completable.create { emitter ->
            database.collection("users").document(id).update("nick",newNick)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    fun getUserNick(id: String): Single<String>{
        return Single.create { emitter ->
            database.collection("users").document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    val obj = documentSnapshot.toObject(User::class.java)
                    emitter.onSuccess(obj!!.nick)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun getUserGamesWon(id: String): Single<Int>{
        return Single.create { emitter ->
            database.collection("users").document(id).get()
                .addOnSuccessListener {
                        documentSnapshot ->
                    val obj = documentSnapshot.toObject(User::class.java)
                    emitter.onSuccess(obj!!.gamesWon)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun updateUserGamesWon(id: String, gamesWon: Int): Completable{
        return Completable.create { emitter ->
            database.collection("users").document(id).update("gamesWon",gamesWon)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun getUsers(): Single<List<User>> {
        return Single.create { emitter ->
            database.collection("users").orderBy("gamesWon",Query.Direction.DESCENDING).get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = documentSnapshot.toObjects(User::class.java)
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }    }

}