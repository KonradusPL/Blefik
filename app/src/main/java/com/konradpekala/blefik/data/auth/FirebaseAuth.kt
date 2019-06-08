package com.konradpekala.blefik.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuth @Inject constructor(): Auth {

    val auth = FirebaseAuth.getInstance()
    override fun isUserLoggedIn():Boolean {
        return auth.currentUser != null
    }

    override fun signUp(email: String, password: String): Single<String> {
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

    override fun signIn(email: String, password: String): Single<String> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email,password)
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

    override fun logOut(){
        auth.signOut()
    }

    override fun getUserId() = auth.currentUser?.uid ?: ""
}