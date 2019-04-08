package com.konradpekala.blefik.data.repo.profile

import io.reactivex.Completable

class LocalProfileRepository: ProfileRepository {
    override fun saveImage() {

    }

    override fun getImage() {
    }

    override fun changeNick(newNick: String): Completable {
        TODO("cache is doing work")
    }
}