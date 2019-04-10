package com.konradpekala.blefik.data.storage

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FileDownloadTask
import com.google.android.gms.tasks.OnSuccessListener



class FirebaseStorage {

    val profilesStorage = FirebaseStorage.getInstance().getReference("profile_images")

    fun getProfileImage(id: String): Single<File>{
        val profileRef = profilesStorage.child(id)

        return Single.create { emitter ->
            val localFile = File.createTempFile("images", "jpg")

            profileRef.getFile(localFile).addOnSuccessListener { taskSnapshot: FileDownloadTask.TaskSnapshot? ->
                emitter.onSuccess(localFile)
            }.addOnFailureListener(OnFailureListener {
                // Handle any errors
            })
        }
    }

    fun saveImage(path: String,id: String): Single<String> {
        return Single.create { emitter ->
            val stream = FileInputStream(File(path))

            val profileReference = profilesStorage.child(id)
            val uploadTask = profileReference.putStream(stream)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation profileReference.downloadUrl
            }).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.d("saveImagepath",downloadUri.toString())
                    emitter.onSuccess(downloadUri.toString())
                } else {

                }
            }
        }
    }
}