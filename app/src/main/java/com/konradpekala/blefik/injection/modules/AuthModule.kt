package com.konradpekala.blefik.injection.modules

import com.google.firebase.auth.FirebaseAuth
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import com.konradpekala.blefik.data.auth.UserSession
import dagger.Module
import dagger.Provides

@Module
 class AuthModule {
    @Provides  fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides fun provideUserSession(auth: FirebaseAuth): UserSession =
        FirebaseUserSession(auth)
}