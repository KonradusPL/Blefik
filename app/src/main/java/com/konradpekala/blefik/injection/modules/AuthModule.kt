package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.auth.UserSession
import com.konradpekala.blefik.data.auth.FirebaseUserSession
import dagger.Module
import dagger.Provides

@Module
 class AuthModule {
    @Provides  fun provideFirebaseAuth() = com.google.firebase.auth.FirebaseAuth.getInstance()

    @Provides fun provideAuth(auth: com.google.firebase.auth.FirebaseAuth): UserSession = FirebaseUserSession(auth)
}