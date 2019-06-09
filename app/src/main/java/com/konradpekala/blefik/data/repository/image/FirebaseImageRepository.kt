package com.konradpekala.blefik.data.repository.image

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class FirebaseImageRepository @Inject constructor(): IImageRepository {

    val profilesStorage = FirebaseStorage.getInstance().getReference("profile_images")

    var localImage: File? = null
    private var mDownloadUrl: String? = null

    fun clearOldImageReference(){
        localImage = null
        mDownloadUrl = null
    }

    override fun clean() = clearOldImageReference()


    override fun getProfileImage(id: String): Single<File>{
        val profileRef = profilesStorage.child(id)

        if (localImage != null)
            return Single.just(localImage)

        return Single.create { emitter ->
            val localFile = File.createTempFile("images", "jpg")

            localImage = localFile

            profileRef.getFile(localFile).addOnSuccessListener { taskSnapshot: FileDownloadTask.TaskSnapshot? ->
                emitter.onSuccess(localFile)
            }.addOnFailureListener(OnFailureListener {
                // Handle any errors
            })
        }
    }

    override fun getImageUrl() = mDownloadUrl ?: ""

    override fun saveImage(imagePath: String, id: String): Completable {
        clearOldImageReference()
        return Completable.create { emitter ->
            val stream = FileInputStream(File(imagePath))

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
                    mDownloadUrl = downloadUri.toString()
                    Log.d("saveImagepath",downloadUri.toString())
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable("error saving image"))
                    Log.d("saveImage",task.exception.toString())
                }
            }
        }
    }
}