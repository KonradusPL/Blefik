package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.auth.Auth
import com.konradpekala.blefik.data.auth.FirebaseAuth
import dagger.Binds
import dagger.Module

@Module
abstract class AuthModule {
    @Binds  abstract fun bindAuth(auth: FirebaseAuth): Auth
}