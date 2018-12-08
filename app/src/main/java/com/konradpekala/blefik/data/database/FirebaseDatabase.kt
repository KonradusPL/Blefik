package com.konradpekala.blefik.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.konradpekala.blefik.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

class FirebaseDatabase: Database {

    private val database = FirebaseFirestore.getInstance()

    private val settings = FirebaseFirestoreSettings.Builder()
        .setTimestampsInSnapshotsEnabled(true)
        .build()

    init {
        database.firestoreSettings = settings
    }

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


}