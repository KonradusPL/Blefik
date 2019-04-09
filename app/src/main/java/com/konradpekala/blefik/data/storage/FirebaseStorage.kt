package com.konradpekala.blefik.data.storage

import android.graphics.Bitmap
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class FirebaseStorage {

    val profilesStorage = FirebaseStorage.getInstance().getReference("profile_images")


    fun saveImage(bitmap: Bitmap,id: String): Single<String> {
        return Single.create { emitter ->
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val profileReference = profilesStorage.child(id)

            val uploadTask = profileReference.putBytes(data)

            uploadTask.addOnFailureListener {
                exception -> emitter.onError(exception)
            }.addOnSuccessListener {taskSnapshot ->
                emitter.onSuccess(taskSnapshot.toString())
            }

        }
    }

    fun saveImage(path: String,id: String): Single<String> {
        return Single.create { emitter ->
            val stream = FileInputStream(File(path))

            val profileReference = profilesStorage.child(id)
            val task = profileReference.putStream(stream)

            task.addOnFailureListener {
                    exception -> emitter.onError(exception)
            }.addOnSuccessListener { taskSnapshot ->
                emitter.onSuccess(taskSnapshot.toString())
            }
        }
    }
}