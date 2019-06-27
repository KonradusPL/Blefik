package com.konradpekala.blefik.data.repository.auth

import com.konradpekala.blefik.data.auth.FirebaseUserSession

class AuthFirebaseRepository(val firebaseUserSession: FirebaseUserSession): IAuthRepository {
    override fun getId(): String {
        return firebaseUserSession.getUserId()
    }

    override fun logOut() {
        firebaseUserSession.logOut()
    }

}