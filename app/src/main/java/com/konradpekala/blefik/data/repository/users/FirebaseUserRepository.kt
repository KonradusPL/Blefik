package com.konradpekala.blefik.data.repository.users

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.model.Image
import com.konradpekala.blefik.data.model.user.User
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.utils.schedulers.ValueToUpdate
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(val userSession: UserSession,
                                                 val storage: FirebaseStorage): IUserRepository.Remote {

    private val TAG = "saveUser"

    private val database = FirebaseFirestore.getInstance()

    private val mUserValueMapper = FirebaseUserUpdateTypeMapper()

    override fun saveImageUrl(url: String): Completable {
        Log.d(TAG,"saveImageUrl")

        val image = Image()
        image.url = url

        return Completable.create { emitter ->
            Log.d("saveImageUrl",url)
            database.collection("users").document(userSession.getUserId()).update("image",image)
                .addOnSuccessListener {
                    Log.d("saveImageUrl","success")
                    emitter.onComplete()
                }.addOnCanceledListener {
                    Log.d("saveImageUrl","cancelled")
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace())
                    Log.d("saveImageUrl",exception.toString())
                }
        }
    }

    override fun clean() {
        storage.clean()
    }

    override fun setNick(newNick: String): Completable {
        return Completable.create { emitter ->
            database.collection("users").document(userSession.getUserId()).update("nick",newNick)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun saveUser(user: User): Completable {
        Log.d(TAG,"saveUser: $user")
        return Completable.create { emitter ->
            Log.d(TAG,"saveUser: completable.create")
            database.collection("users").document(user.id).set(user)
                .addOnCanceledListener {Log.d(TAG,"saveUser: cancelled")}
                .addOnSuccessListener { emitter.onComplete() }
                .addOnCompleteListener { Log.d(TAG,"saveUser: completed") }
                .addOnFailureListener { exception ->
                    Log.d(TAG,exception.toString()) }
        }
    }

    override fun updateValue(userId: String, value: Any, valueType: ValueToUpdate): Completable {
        val valueName = mUserValueMapper.map(valueType)

        Log.d(TAG,"updateValue: $value")
        return Completable.create { emitter ->
            Log.d(TAG,"updateValue: completable.create")
            database.collection("users").document(userId).update(valueName,value)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { exception -> emitter.onError(exception)}
        }
    }

    override fun getNick(id: String): Single<String> {
        return Single.create { emitter ->
            database.collection("users").document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    val obj = documentSnapshot.toObject(User::class.java)
                    emitter.onSuccess(obj!!.nick)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun getAllUsers(): Single<List<User>> {
        return Single.create { emitter ->
            database.collection("users").orderBy("gamesWon", Query.Direction.DESCENDING).get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = documentSnapshot.toObjects(User::class.java)
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun getUserGamesWon(id: String): Single<Int>{
        return getUser(id).map { user: User -> user.gamesWon }
    }

    override fun getUser(id: String): Single<User> {
        return Single.create { emitter ->
            database.collection("users").document(id).get()
                .addOnSuccessListener { documentSnapshot ->
                    val obj = documentSnapshot.toObject(User::class.java)
                    emitter.onSuccess(obj!!)
                }
                .addOnFailureListener { exception ->  emitter.onError(exception) }
        }
    }
}