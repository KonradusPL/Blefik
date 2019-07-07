package com.konradpekala.blefik.data.repository.image

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.konradpekala.blefik.data.base.FileStorage
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import javax.inject.Inject

class FirebaseImageStorage @Inject constructor(): FileStorage {

    private val TAG = "FirebaseImageStorage"

    val profilesStorage = FirebaseStorage.getInstance().getReference("profile_images")

    private var mDownloadUrl: String? = null

    fun clearOldImageReference(){
        mDownloadUrl = null
    }

    fun clean() = clearOldImageReference()

    override fun downloadFile(fileName: String): Single<File>{
        val profileRef = profilesStorage.child(fileName)
        Log.d(TAG,profileRef.path)

        return Single.create { emitter ->
            val localFile = File.createTempFile("images", "jpg")

            profileRef.getFile(localFile).addOnSuccessListener { taskSnapshot: FileDownloadTask.TaskSnapshot? ->
                emitter.onSuccess(localFile)
            }.addOnFailureListener { exception: Exception ->
                emitter.onError(exception)
            }
        }
    }

    override fun getFilePath(): Single<String> = Single.just(mDownloadUrl)

    override fun uploadFile(file: File, lastPathSegment: String): Completable {
        clearOldImageReference()

        val stream = FileInputStream(file)

        val profileReference = profilesStorage.child(lastPathSegment)
        val uploadTask = profileReference.putStream(stream)

        return Completable.create { emitter ->
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        emitter.onError(it)
                    }
                }
                return@Continuation profileReference.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    mDownloadUrl = result.toString()
                    Log.d(TAG,result.toString())
                    emitter.onComplete()
                } else {
                    emitter.onError(Throwable("error saving image"))
                    Log.d(TAG,task.exception.toString())
                }
            }
        }
    }
}