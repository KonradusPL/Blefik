package com.konradpekala.blefik.data.repo.profile

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
        return Completable.create { emitter ->
            database.collection("users").document(auth.getUserId()).update("imageUrl",url)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
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

    override fun saveImage(imagePath: String): Single<String> {
        return storage.saveImage(imagePath,auth.getUserId())
    }

    override fun getProfileImage(): Single<File> {
        return storage.getProfileImage(auth.getUserId())
    }

    override fun setNick(newNick: String): Completable {
        return Completable.create { emitter ->
            database.collection("users").document(auth.getUserId()).update("nick",newNick)
                .addOnSuccessListener { emitter.onComplete()}
                .addOnFailureListener { exception ->  emitter.onError(exception.fillInStackTrace()) }
        }

    }
}