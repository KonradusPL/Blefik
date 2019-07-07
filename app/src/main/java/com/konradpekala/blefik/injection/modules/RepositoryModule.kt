package com.konradpekala.blefik.injection.modules

import com.konradpekala.blefik.data.base.FileStorage
import com.konradpekala.blefik.data.repository.image.FirebaseImageStorage
import com.konradpekala.blefik.data.repository.image.IImageRepository
import com.konradpekala.blefik.data.repository.image.LocalImageRepository
import com.konradpekala.blefik.data.repository.room.FirebaseRoomsRepository
import com.konradpekala.blefik.data.repository.room.IRoomsRepository
import com.konradpekala.blefik.data.repository.users.FirebaseUserRepository
import com.konradpekala.blefik.data.repository.users.IUserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteProfileRepository(repo: FirebaseUserRepository): IUserRepository.Remote

    @Binds
    @Named("ImageRepoRemote")
    abstract fun bindImageRepoRemote(repo: FirebaseImageStorage): FileStorage

    @Binds
    @Named("ImageRepoLocal")
    abstract fun bindImageRepoLocal(repo: LocalImageRepository): IImageRepository

    @Binds
    abstract fun bindRoomsRepoRemote(repo: FirebaseRoomsRepository): IRoomsRepository.Remote
}