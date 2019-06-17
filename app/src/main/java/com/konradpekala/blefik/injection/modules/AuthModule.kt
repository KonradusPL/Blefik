package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
 class AuthModule {
    @Provides  fun provideFirebaseAuth() = com.google.firebase.auth.FirebaseAuth.getInstance()

    @Provides fun provideAuth(auth: com.google.firebase.auth.FirebaseAuth): Auth = FirebaseAuth(auth)
}