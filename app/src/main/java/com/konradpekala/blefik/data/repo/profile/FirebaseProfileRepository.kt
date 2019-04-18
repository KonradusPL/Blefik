package com.konradpekala.blefik.data.repo.profile

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.konradpekala.blefik.data.auth.FirebaseAuth
import com.konradpekala.blefik.data.storage.FirebaseStorage
import com.konradpekala.blefik.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

class FirebaseProfileRepository(val auth: FirebaseAuth,
                                val storage: FirebaseStorage): IProfileRepository {

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

    override fun getNick(): String {
        TODO("cache does work") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clean() {
        storage.clean()
    }

    override fun getEmail(): String {
        TODO("cache does work") //To change body of created functions use File | Settings | File Templates.
    }


    override fun setNick(newNick: String): Completable {
        return Completable.create { emitter ->
            database.collection("users").document(auth.getUserId()).update("nick",newNick)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }

    }
}