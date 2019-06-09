package com.konradpekala.blefik.data.repository.profile

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.model.User
import com.konradpekala.blefik.data.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class FirebaseProfileRepository @Inject constructor(val auth: FirebaseAuth,
                                val storage: FirebaseStorage): IProfileRepository.Remote {

    private val TAG = "FirebaseProfileRepo"

    private val database = FirebaseFirestore.getInstance()


    override fun saveImageUrl(url: String): Completable {
        Log.d("saveImageUrl","start")
        return Completable.create { emitter ->
            Log.d("saveImageUrl",url)
            Log.d("saveImageUrl",url)
            database.collection("users").document(auth.getUserId()).update("imageUrl",url)
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
            database.collection("users").document(auth.getUserId()).update("nick",newNick)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }
    }

    override fun saveProfile(profile: User): Completable {
        Log.d(TAG,"saveProfile")
        return Completable.create { emitter ->
            database.collection("users").document(profile.id).set(profile)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
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
        }    }
}