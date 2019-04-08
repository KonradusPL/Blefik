package com.konradpekala.blefik.data.repo.auth

import com.konradpekala.blefik.data.auth.FirebaseAuth

class AuthRepositoryImpl(val firebaseAuth: FirebaseAuth): AuthRepository {
    override fun logOut() {
        firebaseAuth.logOut()
    }

}