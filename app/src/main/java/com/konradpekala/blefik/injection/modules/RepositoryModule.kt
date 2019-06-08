package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.repository.profile.FirebaseProfileRepository
import com.konradpekala.blefik.data.repository.profile.IProfileRepository
import com.konradpekala.blefik.data.repository.profile.ProfileRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteProfileRepository(repo: FirebaseProfileRepository): IProfileRepository.Remote
}