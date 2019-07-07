package com.konradpekala.blefik.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserSession @Inject constructor(private val auth: FirebaseAuth): UserSession {

    override fun isUserLoggedIn():Boolean {
        return auth.currentUser != null
    }

    override fun signUp(email: String, password: String): Completable{
        return Completable.create { emitter ->
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Log.d("createUserWithEmail", "success")
                        Log.d("createUserWithEmail",System.currentTimeMillis().toString())
                        emitter.onComplete()
                    }else{
                        Log.d("createUserWithEmail:e", task.exception.toString())
                        emitter.onError(task.exception!!.fillInStackTrace())
                    }
                }
        }
    }

    override fun updateEmail(email: String): Completable {
        return Completable.create { emitter ->
            auth.currentUser!!.updateEmail(email).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d("updateEmail", "success")
                    emitter.onComplete()
                }else{
                    Log.d("updateEmail", task.exception.toString())
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
                        Log.d("createUserWithEmail:s", task.exception.toString())
                        emitter.onSuccess(auth.currentUser!!.uid)
                    }else{
                        Log.d("createUserWithEmail:e", task.exception.toString())
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