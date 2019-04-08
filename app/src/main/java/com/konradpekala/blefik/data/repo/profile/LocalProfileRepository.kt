package com.konradpekala.blefik.data.repo.profile

import io.reactivex.Completable

class LocalProfileRepository: ProfileRepository {
    override fun getNick(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEmail(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveImage() {

    }

    override fun getImage() {
    }

    override fun changeNick(newNick: String): Completable {
        TODO("cache is doing work")
    }
}