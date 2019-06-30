package com.konradpekala.blefik.data.repository.auth

import com.konradpekala.blefik.data.auth.UserSession

class AuthFirebaseRepository(val userSession: UserSession): IAuthRepository {
    override fun getId(): String {
        return userSession.getUserId()
    }

    override fun logOut() {
        userSession.logOut()
    }

}