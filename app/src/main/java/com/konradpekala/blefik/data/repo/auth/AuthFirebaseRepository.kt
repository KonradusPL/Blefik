package com.konradpekala.blefik.data.repo.auth

import com.konradpekala.blefik.data.auth.FirebaseAuth

class AuthFirebaseRepository(val firebaseAuth: FirebaseAuth): IAuthRepository {
    override fun getId(): String {
        return firebaseAuth.getUserId()
    }

    override fun logOut() {
        firebaseAuth.logOut()
    }

}