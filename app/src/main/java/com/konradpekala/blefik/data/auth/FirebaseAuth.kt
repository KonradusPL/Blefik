package com.konradpekala.blefik.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single

class FirebaseAuth {

    val auth = FirebaseAuth.getInstance()
    fun isUserLoggedIn():Boolean {
        return auth.currentUser != null
    }

    fun signUp(email: String, password: String): Single<String> {
        return Single.create { emitter ->
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.w("createUserWithEmail:s", task.exception)
                        emitter.onSuccess(auth.currentUser!!.uid)
                    }else{
                        Log.w("createUserWithEmail:e", task.exception)
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    fun signIn(email: String, password: String): Completable {
        return Completable.create { emitter ->
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.w("createUserWithEmail:s", task.exception)
                        emitter.onComplete()
                    }else{
                        Log.w("createUserWithEmail:e", task.exception)
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    fun logOut(){
        auth.signOut()
    }

    fun getUserId() = auth.currentUser?.uid ?: ""
}